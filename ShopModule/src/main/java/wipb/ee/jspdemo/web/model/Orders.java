package wipb.ee.jspdemo.web.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private Long customerId;
    private String productName;
    private int productCount;
    private BigDecimal total;
    private boolean isPaid;
    private boolean isCancelled;
    public Orders(){
    }

    public Orders(Long customerId, String productName, int productCount, BigDecimal total, boolean isPaid, boolean isCancelled) {
        this.customerId = customerId;
        this.productName = productName;
        this.productCount = productCount;
        this.total = total;
        this.isPaid = isPaid;
        this.isCancelled = isCancelled;
    }

    public Orders(Long id, Long customerId, String productName, int productCount, BigDecimal total, boolean isPaid, boolean isCancelled) {
        this.id = id;
        this.customerId = customerId;
        this.productName = productName;
        this.productCount = productCount;
        this.total = total;
        this.isPaid = isPaid;
        this.isCancelled = isCancelled;
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
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
