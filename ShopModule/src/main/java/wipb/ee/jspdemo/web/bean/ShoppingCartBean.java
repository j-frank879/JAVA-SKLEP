package wipb.ee.jspdemo.web.bean;

import wipb.ee.jspdemo.web.model.Product;

import java.util.List;

public class ShoppingCartBean {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
