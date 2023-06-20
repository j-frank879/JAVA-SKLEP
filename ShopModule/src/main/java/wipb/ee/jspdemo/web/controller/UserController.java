package wipb.ee.jspdemo.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wipb.ee.jspdemo.web.bean.UserBean;
import wipb.ee.jspdemo.web.dao.UserDao;
import wipb.ee.jspdemo.web.model.Product;
import wipb.ee.jspdemo.web.model.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

//MyBean myBean = (MyBean) request.getSession().getAttribute("myBean");
@WebServlet(name = "UserController", urlPatterns = {"/registry", "/login", "/logout"})
public class UserController extends HttpServlet {
    private final Logger log = Logger.getLogger(ProductController.class.getName());
    private UserDao dao = new UserDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/registry":
                handleRegistry(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            case "/login":
                handleLogin(request, response);
                break;
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.equals("/login")) {
            handleLoginPost(request, response);
        }
        if (path.equals("/registry")) {
            handleRegistryPost(request, response);
        }

    }
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("login","");
        request.setAttribute("password","");
        request.getRequestDispatcher("/WEB-INF/views/login_form.jsp").forward(request,response);
    }
    private void handleLoginPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String s = request.getPathInfo();
        Long id = parseId(s);
        String role="customer";
        Map<String,String> fieldToError = new HashMap<>();
        User b = parseLoginPassword(request.getParameterMap(), fieldToError);

        if(!fieldToError.isEmpty()){
            request.setAttribute("errors", fieldToError);
            request.setAttribute("login", request.getParameter("login"));
            request.setAttribute("password", request.getParameter("password"));


            request.getRequestDispatcher("/WEB-INF/views/login_form.jsp").forward(request,response);
            return;
        }
        Optional<User> a=dao.findByLoginPassword(b.getLogin(),b.getPassword());
if(a.isPresent())
{
    UserBean myBean = (UserBean) request.getSession().getAttribute("user");
    myBean.setId(a.get().getId());
    myBean.setLogin(a.get().getLogin());
    myBean.setPassword(a.get().getPassword());
    myBean.setRole(a.get().getRole());
    myBean.setName(a.get().getName());
    myBean.setEmail(a.get().getEmail());
    myBean.setBalance(a.get().getBalance());

}
  else
  {}

    }

    private void handleRegistryPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String s = request.getPathInfo();
        Long id = parseId(s);
String role="customer";
        Map<String,String> fieldToError = new HashMap<>();
        User b = parseUser(request.getParameterMap(), fieldToError);

        if(!fieldToError.isEmpty()){
            request.setAttribute("errors", fieldToError);
            request.setAttribute("name", request.getParameter("name"));
            request.setAttribute("email", request.getParameter("email"));
            request.setAttribute("login", request.getParameter("login"));
            request.setAttribute("password", request.getParameter("password"));


            request.getRequestDispatcher("/WEB-INF/views/registry_form.jsp").forward(request,response);
            return;
        }
        b.setId(id);
        b.setRole("customer");

        dao.save(b);
        response.sendRedirect(request.getContextPath() + "/login");
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
       UserBean myBean = (UserBean) request.getSession().getAttribute("user");
       myBean.clear();
       //request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/product/list");
    }

    private void handleRegistry(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("login","");
        request.setAttribute("password","");
        request.setAttribute("name","");
        request.setAttribute("email","");
        request.getRequestDispatcher("/WEB-INF/views/registry_form.jsp").forward(request,response);
    }


    private User parseUser(Map<String,String[]> paramToValue, Map<String,String> fieldToError) {
        String name = paramToValue.get("name")[0];
        String login = paramToValue.get("login")[0];
        String password = paramToValue.get("password")[0];
        String email = paramToValue.get("email")[0];

        if (name == null || name.trim().isEmpty()) {
            fieldToError.put("name","Pole name nie może być puste");
        }
        if (email == null || email.trim().isEmpty()) {
            fieldToError.put("email","Pole email nie może być puste");
        }
        if (login == null || login.trim().isEmpty()) {
            fieldToError.put("name","Pole login nie może być puste");
        }
        if (password == null || password.trim().isEmpty()) {
            fieldToError.put("name","Pole password nie może być puste");
        }

        return fieldToError.isEmpty() ?  new User(login,password,name,email) : null;
    }
    private User parseLoginPassword(Map<String,String[]> paramToValue, Map<String,String> fieldToError)
    {String login = paramToValue.get("login")[0];
        String password = paramToValue.get("password")[0];
        if (login == null || login.trim().isEmpty()) {
            fieldToError.put("name","Pole login nie może być puste");
        }
        if (password == null || password.trim().isEmpty()) {
            fieldToError.put("name","Pole password nie może być puste");
        }
        return fieldToError.isEmpty() ?  new User(login,password) : null;
    }
    private Long parseId(String s) {
        if (s == null || !s.startsWith("/"))
            return null;
        return Long.parseLong(s.substring(1));
    }

}
