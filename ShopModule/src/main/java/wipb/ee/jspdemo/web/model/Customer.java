package wipb.ee.jspdemo.web.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String name;
    private String email;
    private BigDecimal balance;
    private Long userId;

    public Customer() {
    }


    public Customer(Long id, String name, String email, BigDecimal balance, Long userId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
