package wipb.ee.jspdemo.web.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedQuery(name = "Orders.findAll", query = "select b from Orders b")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private Long customerId;
    private String productName;
    private int productCount;
    public Orders(){
    }

    public Orders(Long id, Long customerId, String productName, int productCount) {
        this.id = id;
        this.customerId = customerId;
        this.productName = productName;
        this.productCount = productCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
