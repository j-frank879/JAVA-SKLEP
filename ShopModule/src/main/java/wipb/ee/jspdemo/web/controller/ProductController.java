package wipb.ee.jspdemo.web.controller;


import jakarta.annotation.security.DeclareRoles;
import wipb.ee.jspdemo.web.dao.ProductDao;
import wipb.ee.jspdemo.web.model.Product;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@WebServlet(name = "ProductController", urlPatterns = {"/product/list", "/product/edit/*", "/product/remove/*", "/product/add"})
public class ProductController extends HttpServlet {

    private final Logger log = Logger.getLogger(ProductController.class.getName());
   @EJB private ProductDao dao = new ProductDao();
    @Override
    public void init() throws ServletException {
        super.init();
        FileHandler fh;
        try {
            fh = new FileHandler("./productController.txt");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (Exception __) {}
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/product/list":
                handleProductList(request, response);
                break;
            case "/product/edit":
                handleGetEditGet(request, response);
                break;
            case "/product/remove":
                handleProductRemove(request, response);
                break;
            case "/product/add":
                handleProductAdd(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.equals("/product/edit")) {
            handleProductEditPost(request, response);
        }
        if(path.equals("/product/add")){
            handleProductAddPost(request,response);
        }
    }

    private void handleProductList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = dao.findAll();
        request.setAttribute("productList", products);
        request.getRequestDispatcher("/WEB-INF/views/product_list.jsp").forward(request, response);
    }

    private void handleGetEditGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getPathInfo();
        Long id = parseId(s);
        Product b;
        if (id != null) {
            b = dao.find(id).orElseThrow(() -> new IllegalStateException("No product with id "+id));
            request.setAttribute("name",b.getName());
            request.setAttribute("price",formatPrice(b.getPrice()));
        }
        request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request, response);
    }

    private void handleProductAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name","");
        request.setAttribute("price","");
        request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request,response);
    }
    private void handleProductEditPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getPathInfo();
        Long id = parseId(s);

        Map<String,String> fieldToError = new HashMap<>();
        Product b = parseProduct(request.getParameterMap(),fieldToError);

        if (!fieldToError.isEmpty()) {
            request.setAttribute("errors",fieldToError);
            request.setAttribute("name",request.getParameter("name"));
            request.setAttribute("price",request.getParameter("price"));

            request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request, response);
            return;
        }

        b.setId(id);
        dao.update(b);

        response.sendRedirect(request.getContextPath() + "/product/list");
    }
    private void handleProductAddPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getPathInfo();
        Long id = parseId(s);

        Map<String,String> fieldToError = new HashMap<>();
        Product b = parseProduct(request.getParameterMap(), fieldToError);

        if(!fieldToError.isEmpty()){
            request.setAttribute("errors", fieldToError);
            request.setAttribute("name", request.getParameter("name"));
            request.setAttribute("price", request.getParameter("price"));

            request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request,response);
            return;
        }
        b.setId(id);
        dao.save(b);
        response.sendRedirect(request.getContextPath() + "/product/list");
    }

    private void handleProductRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String s = request.getPathInfo();
        Long id = parseId(s);
        dao.delete(id);
        response.sendRedirect(request.getContextPath() + "/product/list");
    }

    private Product parseProduct(Map<String,String[]> paramToValue, Map<String,String> fieldToError) {
        String name = paramToValue.get("name")[0];
        String price = paramToValue.get("price")[0];
        BigDecimal bdPrice = null;

        if (name == null || name.trim().isEmpty()) {
            fieldToError.put("name","Pole tytuł nie może być puste");
        }


        if (price == null || price.trim().isEmpty()) {
            fieldToError.put("price","Pole cena nie może być puste");
        } else {
            try {
                bdPrice = parsePrice(price);
            } catch (Throwable e) {
                fieldToError.put("price","Cena musi być poprawną liczbą");
            }
        }

        return fieldToError.isEmpty() ?  new Product(name,bdPrice) : null;
    }


    private BigDecimal parsePrice(String s) throws ParseException {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        Locale locale = new Locale("pl", "PL");
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        format.setParseBigDecimal(true);
        return ((BigDecimal)format.parse(s)).setScale(2, RoundingMode.FLOOR);
    }

    private String formatPrice(BigDecimal price)  {
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
