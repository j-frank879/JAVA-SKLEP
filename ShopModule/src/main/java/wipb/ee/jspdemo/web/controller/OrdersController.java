package wipb.ee.jspdemo.web.controller;


import jakarta.ejb.AfterBegin;
import jakarta.ejb.BeforeCompletion;
import jakarta.ejb.EJB;
import jakarta.ejb.Startup;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.OnError;
import wipb.ee.jspdemo.web.bean.UserBean;
import wipb.ee.jspdemo.web.dao.OrdersDao;
import wipb.ee.jspdemo.web.dao.ProductDao;
import wipb.ee.jspdemo.web.dao.UserDao;
import wipb.ee.jspdemo.web.model.Orders;
import wipb.ee.jspdemo.web.model.Product;
import wipb.ee.jspdemo.web.model.User;
import wipb.ee.jspdemo.web.services.EmailService;
import wipb.ee.jspdemo.web.services.MailBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

@WebServlet(name = "OrdersController", urlPatterns = {
        "/orders/listCancelled", "/orders/listPaid", "/orders/listAll", "/orders/list",
        "/orders/cancel/*", "/orders/add", "/orders/pay/*", "/deposit"})
public class OrdersController extends HttpServlet {
    private final EmailService emailService = new EmailService();

    private final Logger log = Logger.getLogger(OrdersController.class.getName());
    @EJB
    private OrdersDao dao = new OrdersDao();

    public OrdersController() throws ServletException {
        init();
    }

    public void init() throws ServletException {
        super.init();
        FileHandler fh;
        try {
            fh = new FileHandler("./ordersController.txt");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (Exception __) {}
    }

    private UserBean getCurrentuser(HttpServletRequest request) {
        return (UserBean) request.getSession().getAttribute("user");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/orders/list":
                handleOrdersListUnflagged(request, response);
                break;
            case "/orders/listCancelled":
                handleOrdersListCancelled(request, response);
                break;
            case "/orders/listPaid":
                handleOrdersListPaid(request, response);
                break;
            case "/orders/listAll":
                handleOrdersListAll(request, response);
                break;
            case "/orders/cancel":
                try {
                    handleOrdersCancel(request, response);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/orders/add":
                handleOrdersAdd(request, response);
                break;
            case "/orders/pay":
                handleOrdersPayPost(request, response);
                break;
            case "/deposit":
                handleDepositAdd(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.equals("/orders/add")) {
            handleOrdersAddPost(request, response);
        }
    }

    private List<Orders> filterGetCancelled(List<Orders> ordersList) {
        ordersList = ordersList.stream().filter(a -> a.isCancelled() == true).collect(Collectors.toList());
        return ordersList;
    }

    private List<Orders> filterGetPaid(List<Orders> ordersList) {
        ordersList = ordersList.stream().filter(a -> a.isPaid() == true).collect(Collectors.toList());
        return ordersList;
    }

    private List<Orders> filterGetUnflagged(List<Orders> ordersList) {
        ordersList = ordersList.stream().filter(a -> a.isCancelled() == false && a.isPaid() == false).collect(Collectors.toList());
        return ordersList;
    }

    private boolean isCurrentUserAdmin(HttpServletRequest request) {
        return ((UserBean) request.getSession().getAttribute("user")).getRole().equals("worker");
    }

    private List<Orders> getAllByUser(HttpServletRequest request) {
        List<Orders> orders = dao.findAll();
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        Long customerId = user.getId();
        if (!isCurrentUserAdmin(request)) {
            orders = orders.stream().filter(a -> a.getCustomerId() == customerId).collect(Collectors.toList());
        }
        return orders;
    }

    private void handleOrdersListAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Orders> orders = getAllByUser(request);

        request.setAttribute("ordersList", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders_list.jsp").forward(request, response);
    }

    private void handleOrdersListUnflagged(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Orders> orders = getAllByUser(request);
        orders = filterGetUnflagged(orders);

        request.setAttribute("ordersList", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders_list.jsp").forward(request, response);
    }

    private void handleOrdersListCancelled(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Orders> orders = getAllByUser(request);
        orders = filterGetCancelled(orders);

        request.setAttribute("ordersList", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders_list.jsp").forward(request, response);
    }

    private void handleOrdersListPaid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Orders> orders = getAllByUser(request);
        orders = filterGetPaid(orders);

        request.setAttribute("ordersList", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders_list.jsp").forward(request, response);
    }

    private void handleDepositAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("userId", getCurrentuser(request));
        request.getRequestDispatcher("/WEB-INF/views/deposit.jsp").forward(request, response);
    }

    private void handleDepositAddPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BigDecimal depositAmount = BigDecimal.valueOf((Long) request.getSession().getAttribute("depositAmount"));
        UserDao userDao = new UserDao();
        UserBean userBean = getCurrentuser(request);
        userBean.setBalance(userBean.getBalance().add(depositAmount));
        User u = new User(userBean);
        userDao.save(u);
        response.sendRedirect(request.getContextPath() + "/orders/list");
    }

    private void handleOrdersAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("id", "");
        ProductDao productDao = new ProductDao();
        List<Product> productList = productDao.findAll();
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("/WEB-INF/views/orders_form.jsp").forward(request, response);
    }

    private void handleOrdersAddPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getPathInfo();
        Long id = null;

        Map<String, String> fieldToError = new HashMap<>();
        Orders b = parseOrders(request.getParameterMap(), fieldToError);
        b.setId(getCurrentuser(request).getId());

        if (!fieldToError.isEmpty()) {
            request.setAttribute("errors", fieldToError);
            request.setAttribute("name", request.getParameter("name"));
            request.setAttribute("price", request.getParameter("price"));

            request.getRequestDispatcher("/WEB-INF/views/orders_form.jsp").forward(request, response);
            return;
        }

        b.setId(id);
        dao.save(b);

        User user = new User(getCurrentuser(request));
        log.info("User id: " + user.getId() + "made a new order for " + b.getProductCount() + b.getProductName());
        response.sendRedirect(request.getContextPath() + "/orders/list");
    }

    private void handleOrdersPayPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getPathInfo();
        Long id = parseId(s);

        Map<String, String> fieldToError = new HashMap<>();
        Orders b = dao.find(id).get();
        b.setCustomerId(getCurrentuser(request).getId());

        UserBean userBean = getCurrentuser(request);

        Boolean fail = false;

        BigDecimal userBalance = userBean.getBalance() == null ? BigDecimal.valueOf(0) : userBean.getBalance();
        if (userBalance.compareTo(b.getTotal()) == -1) {
            fail = true;
        }

        if (fail) {
            log.info("User id: " + userBean.getId() + " tried to pay with insufficient balance (" + userBean.getBalance() + ") for and order totalling " + b.getTotal());
            response.sendRedirect(request.getContextPath() + "/product/list");
            return;
        }

        dao.pay(id);
        response.sendRedirect(request.getContextPath() + "/product/list");
    }

    private void handleMailCancelled(HttpServletRequest request, Optional<Orders> order) throws MessagingException {
        MailBuilder mailBuilder = new MailBuilder();
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        mailBuilder.parseOrdersRequest(user, order);

        String body = mailBuilder.buildCancelledOrderBody();
        emailService.sendEmail(user.getEmail(), "Cancelled order", body);
    }

    private void handleOrdersCancel(HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {
        String s = request.getPathInfo();
        Long id = parseId(s);

        handleMailCancelled(request, dao.find(id));
        dao.cancel(id);
        response.sendRedirect(request.getContextPath() + "/orders/list");
    }

    private Orders parseOrders(Map<String, String[]> paramToValue, Map<String, String> fieldToError) {
        String customerId = "1";

        String productName = paramToValue.get("productName")[0];
        String productCount = paramToValue.get("productCount")[0];

        Long customerIdLong = null;
        int productCountInt = 0;
        boolean isPaidBool = false;
        boolean isCancelledBool = false;

        ProductDao productDao = new ProductDao();
        BigDecimal price = productDao.find(productName).get().getPrice();

        if (productName == null || productName.trim().isEmpty()) {
            fieldToError.put("name", "Name cannot be empty");
        }

        try {
            customerIdLong = Long.parseLong(customerId);
            productCountInt = Integer.parseInt(productCount);
        } catch (Throwable e) {
            fieldToError.put("price", "Price must be a number");
        }

        BigDecimal totalBd = BigDecimal.valueOf(productCountInt).multiply(price);
        return fieldToError.isEmpty() ? new Orders(customerIdLong, productName, productCountInt, totalBd, isPaidBool, isCancelledBool) : null;
    }


    private BigDecimal parsePrice(String s) throws ParseException {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        Locale locale = new Locale("pl", "PL");
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        format.setParseBigDecimal(true);
        return ((BigDecimal) format.parse(s)).setScale(2, RoundingMode.FLOOR);
    }

    private String formatPrice(BigDecimal price) {
        if (price == null) return "";
        Locale locale = new Locale("pl", "PL");
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        return format.format(price);
    }

    private Long parseId(String s) {
        if (s == null || !s.startsWith("/"))
            return null;
        return Long.parseLong(s.substring(1));
    }
}
