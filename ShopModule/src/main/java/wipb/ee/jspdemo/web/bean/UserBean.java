package wipb.ee.jspdemo.web.bean;

import java.math.BigDecimal;

public class UserBean {
    private Long id;
    private String login;
    private String password;
    private String role;
    private String name;
    private String email;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void clear()
    {name=null;
        id=null;
        email=null;
        password=null;
        login=null;
        role=null;
        email=null;
        balance=null;

    }
}
