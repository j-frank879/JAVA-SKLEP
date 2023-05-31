package wipb.ee.jspdemo.web.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedQuery(name = "Customer.findAll", query = "select b from Customer b")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String name;
    private BigDecimal balance;

    public Customer() {
    }

    public Customer(Long id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
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
}
