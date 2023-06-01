/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.ee.jspdemo.web.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wipb.ee.jspdemo.web.dao.OrdersDao;
import wipb.ee.jspdemo.web.dao.ProductDao;
import wipb.ee.jspdemo.web.model.Orders;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(name = "OrdersController", urlPatterns = {"/orders/list", "/orders/cancel", "/orders/add", "/orders/pay"})
public class OrdersController extends HttpServlet {

    private final Logger log = Logger.getLogger(OrdersController.class.getName());
    private OrdersDao dao = new OrdersDao();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/orders/listCancelled":
                //handleOrdersList(request, response);
                break;
            case "/orders/cancel":
                handleOrdersCancel(request, response);
                break;
            case "/orders/add":
                handleOrdersAdd(request, response);
                break;
            case "/orders/list":
                handleOrdersList(request, response);
                break;
            case "/orders/pay":
                handleOrdersPayPost(request, response);
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

    private void handleOrdersList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Orders> orders = dao.findAll();

        /* if admin/manager then
        Long customerId = request.costam();
        orders = (List<Orders>) orders.stream().filter(a->a.getCustomerId() == customerId);
        */

        request.setAttribute("ordersList", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders/orders_list.jsp").forward(request, response);
    }
    /*
    private void handleOrdersListNotCancelled(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Orders> orders = dao.findAllNotCancelled();
        Long customerId = request.costam();
        orders = (List<Orders>) orders.stream().filter(a->a.getCustomerId() == customerId);
        request.setAttribute("ordersList", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders/orders_list.jsp").forward(request, response);
    }
    */
    private void handleOrdersAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name", "");
        request.setAttribute("price", "");
        request.getRequestDispatcher("/WEB-INF/views/orders/orders_form.jsp").forward(request, response);
    }
    private void handleOrdersAddPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getPathInfo();
        Long id = parseLong(s);

        Map<String, String> fieldToError = new HashMap<>();
        Orders b = parseOrders(request.getParameterMap(), fieldToError);

        if (!fieldToError.isEmpty()) {
            request.setAttribute("errors", fieldToError);
            request.setAttribute("name", request.getParameter("name"));
            request.setAttribute("price", request.getParameter("price"));

            request.getRequestDispatcher("/WEB-INF/views/orders/orders_form.jsp").forward(request, response);
            return;
        }
        b.setId(id);
        dao.save(b);
        response.sendRedirect(request.getContextPath() + "/orders/list");
    }
    private void handleOrdersPayPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getPathInfo();
        Long id = parseLong(s);

        Map<String,String> fieldToError = new HashMap<>();
        Orders b = parseOrders(request.getParameterMap(),fieldToError);

        /*
        CustomerDao customerDao = new CustomerDao();
        Long customerId = request.costam()
        boolean result = customerDao.tryDeduct(customerId, b.getTotal())
        if(!result){
            log.info("User with an id: " + customerId + " tried to pay without having sufficient money.");
        }
        */

        dao.pay(id);
        response.sendRedirect(request.getContextPath() + "/product/list");
    }
    private void handleOrdersCancel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String s = request.getPathInfo();
        Long id = parseLong(s);
        dao.cancel(id);
        response.sendRedirect(request.getContextPath() + "/orders/list");
    }

    private Orders parseOrders(Map<String, String[]> paramToValue, Map<String, String> fieldToError) {
        String customerId = paramToValue.get("customerId")[0];
        String productName = paramToValue.get("productName")[0];
        String productCount = paramToValue.get("productCount")[0];

        Long customerIdLong = null;
        int productCountInt = 0;
        boolean isPaidBool = true;
        boolean isCancelledBool = true;

        ProductDao productDao = new ProductDao();
        BigDecimal price = productDao.find(productName).get().getPrice();

        if (productName == null || productName.trim().isEmpty()) {
            fieldToError.put("name", "Pole tytuł nie może być puste");
        }

        try {
            customerIdLong = parseLong(customerId);
            productCountInt = parseInt(productCount);
        } catch (Throwable e) {
            fieldToError.put("price", "Cena musi być poprawną liczbą");
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
    private Long parseLong(String s) {
        if (s == null || !s.startsWith("/"))
            return null;
        return Long.parseLong(s.substring(1));
    }
    private int parseInt(String s) throws ParseException {
        if (s == null || !s.startsWith("/")) {
            throw new ParseException(String.format("Error parsing string", s), 0);
        }
        return Integer.parseInt(s.substring(1));
    }
}
