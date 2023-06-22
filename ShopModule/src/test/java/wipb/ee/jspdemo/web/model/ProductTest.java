package wipb.ee.jspdemo.web.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        product.setId(id);

        Long retrievedId = product.getId();

        assertEquals(id, retrievedId);
    }

    @Test
    public void testGetName() {
        String name = "Example Product";
        product.setName(name);

        String retrievedName = product.getName();

        assertEquals(name, retrievedName);
    }

    @Test
    public void testGetPrice() {
        BigDecimal price = new BigDecimal("100.00");
        product.setPrice(price);

        BigDecimal retrievedPrice = product.getPrice();

        assertEquals(price, retrievedPrice);
    }

    @Test
    public void testSetId() {
        Long id = 1L;
        product.setId(id);

        Long retrievedId = product.getId();

        assertEquals(id, retrievedId);
    }

    @Test
    public void testSetName() {
        String name = "Example Product";
        product.setName(name);

        String retrievedName = product.getName();

        assertEquals(name, retrievedName);
    }

    @Test
    public void testSetPrice() {
        BigDecimal price = new BigDecimal("100.00");
        product.setPrice(price);

        BigDecimal retrievedPrice = product.getPrice();

        assertEquals(price, retrievedPrice);
    }
}