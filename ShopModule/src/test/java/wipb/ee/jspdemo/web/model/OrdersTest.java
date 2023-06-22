package wipb.ee.jspdemo.web.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrdersTest {

    private Orders orders;

    @BeforeEach
    public void setUp() {
        orders = new Orders();
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        orders.setId(id);

        Long retrievedId = orders.getId();

        assertEquals(id, retrievedId);
    }

    @Test
    public void testGetCustomerId() {
        Long customerId = 1L;
        orders.setCustomerId(customerId);

        Long retrievedCustomerId = orders.getCustomerId();

        assertEquals(customerId, retrievedCustomerId);
    }

    @Test
    public void testGetProductName() {
        String productName = "Example Product";
        orders.setProductName(productName);

        String retrievedProductName = orders.getProductName();

        assertEquals(productName, retrievedProductName);
    }

    @Test
    public void testGetProductCount() {
        int productCount = 10;
        orders.setProductCount(productCount);

        int retrievedProductCount = orders.getProductCount();

        assertEquals(productCount, retrievedProductCount);
    }

    @Test
    public void testGetTotal() {
        BigDecimal total = new BigDecimal("100.00");
        orders.setTotal(total);

        BigDecimal retrievedTotal = orders.getTotal();

        assertEquals(total, retrievedTotal);
    }

    @Test
    public void testIsPaid() {
        boolean isPaid = true;
        orders.setPaid(isPaid);

        boolean retrievedIsPaid = orders.isPaid();

        assertEquals(isPaid, retrievedIsPaid);
    }

    @Test
    public void testIsCancelled() {
        boolean isCancelled = true;
        orders.setCancelled(isCancelled);

        boolean retrievedIsCancelled = orders.isCancelled();

        assertEquals(isCancelled, retrievedIsCancelled);
    }

    @Test
    public void testSetId() {
        Long id = 1L;
        orders.setId(id);

        Long retrievedId = orders.getId();

        assertEquals(id, retrievedId);
    }

    @Test
    public void testSetCustomerId() {
        Long customerId = 1L;
        orders.setCustomerId(customerId);

        Long retrievedCustomerId = orders.getCustomerId();

        assertEquals(customerId, retrievedCustomerId);
    }

    @Test
    public void testSetProductName() {
        String productName = "Example Product";
        orders.setProductName(productName);

        String retrievedProductName = orders.getProductName();

        assertEquals(productName, retrievedProductName);
    }

    @Test
    public void testSetProductCount() {
        int productCount = 10;
        orders.setProductCount(productCount);

        int retrievedProductCount = orders.getProductCount();

        assertEquals(productCount, retrievedProductCount);
    }

    @Test
    public void testSetTotal() {
        BigDecimal total = new BigDecimal("100.00");
        orders.setTotal(total);

        BigDecimal retrievedTotal = orders.getTotal();

        assertEquals(total, retrievedTotal);
    }

    @Test
    public void testSetPaid() {
        boolean isPaid = true;
        orders.setPaid(isPaid);

        boolean retrievedIsPaid = orders.isPaid();

        assertEquals(isPaid, retrievedIsPaid);
    }

    @Test
    public void testSetCancelled() {
        boolean isCancelled = true;
        orders.setCancelled(isCancelled);

        boolean retrievedIsCancelled = orders.isCancelled();

        assertEquals(isCancelled, retrievedIsCancelled);
    }
}