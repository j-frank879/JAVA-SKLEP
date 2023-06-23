package wipb.ee.jspdemo.web.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wipb.ee.jspdemo.web.model.Orders;
import wipb.ee.jspdemo.web.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrdersDaoTest {
    private OrdersDao ordersDao;

    @BeforeEach
    public void setUp() {
        ordersDao = new OrdersDao();
    }

    @AfterEach
    public void tearDown() {
        ordersDao.truncate();
    }

    @Test
    public void testSave() {
        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setProductName("Product 1");
        orders.setProductCount(2);
        orders.setTotal(BigDecimal.valueOf(20));
        orders.setPaid(false);
        orders.setCancelled(false);

        ordersDao.save(orders);

        assertNotNull(orders.getId());

        Optional<Orders> savedOrders = ordersDao.find(orders.getId());

        assertTrue(savedOrders.isPresent());
        assertEquals(orders.getCustomerId(), savedOrders.get().getCustomerId());
        assertEquals(orders.getProductName(), savedOrders.get().getProductName());
        assertEquals(orders.getProductCount(), savedOrders.get().getProductCount());
        assertEquals(orders.getTotal(), savedOrders.get().getTotal());
        assertEquals(orders.isPaid(), savedOrders.get().isPaid());
        assertEquals(orders.isCancelled(), savedOrders.get().isCancelled());
    }

    @Test
    public void testCancel() {
        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setProductName("Product 1");
        orders.setProductCount(2);
        orders.setTotal(new BigDecimal("20.00"));
        orders.setPaid(false);
        orders.setCancelled(false);

        ordersDao.save(orders);

        ordersDao.cancel(orders.getId());

        Optional<Orders> cancelledOrders = ordersDao.find(orders.getId());

        assertTrue(cancelledOrders.isPresent());
        assertTrue(cancelledOrders.get().isCancelled());
    }

    @Test
    public void testPay() {
        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setProductName("Product 1");
        orders.setProductCount(2);
        orders.setTotal(new BigDecimal("20.00"));
        orders.setPaid(false);
        orders.setCancelled(false);

        ordersDao.save(orders);

        ordersDao.pay(orders.getId());

        Optional<Orders> paidOrders = ordersDao.find(orders.getId());

        assertTrue(paidOrders.isPresent());
        assertTrue(paidOrders.get().isPaid());
    }

    @Test
    public void testFind() {

        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setProductName("Product 1");
        orders.setProductCount(2);
        orders.setTotal(BigDecimal.valueOf(20));
        orders.setPaid(false);
        orders.setCancelled(false);

        ordersDao.save(orders);

        Optional<Orders> retrievedOrders = ordersDao.find(orders.getId());

        assertTrue(retrievedOrders.isPresent());
        assertEquals(orders.getId(), retrievedOrders.get().getId());
        assertEquals(orders.getCustomerId(), retrievedOrders.get().getCustomerId());
        assertEquals(orders.getProductName(), retrievedOrders.get().getProductName());
        assertEquals(orders.getProductCount(), retrievedOrders.get().getProductCount());
        assertEquals(orders.getTotal(), retrievedOrders.get().getTotal());
        assertEquals(orders.isPaid(), retrievedOrders.get().isPaid());
        assertEquals(orders.isCancelled(), retrievedOrders.get().isCancelled());
    }

    @Test
    public void testFindAll() {
        Orders orders1 = new Orders();
        orders1.setCustomerId(1L);
        orders1.setProductName("Product 1");
        orders1.setProductCount(2);
        orders1.setTotal(new BigDecimal("20.00"));
        orders1.setPaid(false);
        orders1.setCancelled(false);
        ordersDao.save(orders1);

        Orders orders2 = new Orders();
        orders2.setCustomerId(2L);
        orders2.setProductName("Product 2");
        orders2.setProductCount(3);
        orders2.setTotal(new BigDecimal("30.00"));
        orders2.setPaid(true);
        orders2.setCancelled(false);
        ordersDao.save(orders2);


        List<Orders> ordersList = ordersDao.findAll();


        assertEquals(2, ordersList.size());
        assertEquals(orders1.getId(), ordersList.get(0).getId());
        assertEquals(orders2.getId(), ordersList.get(1).getId());
    }

    @Test
    public void testFindAllNotCancelled() {

        Orders orders1 = new Orders();
        orders1.setCustomerId(1L);
        orders1.setProductName("Product 1");
        orders1.setProductCount(2);
        orders1.setTotal(new BigDecimal("20.00"));
        orders1.setPaid(false);
        orders1.setCancelled(false);
        ordersDao.save(orders1);

        Orders orders2 = new Orders();
        orders2.setCustomerId(2L);
        orders2.setProductName("Product 2");
        orders2.setProductCount(3);
        orders2.setTotal(new BigDecimal("30.00"));
        orders2.setPaid(true);
        orders2.setCancelled(true);
        ordersDao.save(orders2);


        List<Orders> notCancelledOrders = ordersDao.findAllNotCancelled();


        assertEquals(1, notCancelledOrders.size());
        assertFalse(notCancelledOrders.get(0).isCancelled());
        assertEquals(notCancelledOrders.get(0).getId(), orders1.getId());
    }

    @Test
    public void testFindAllByCustomerId() {

        Orders orders1 = new Orders();
        orders1.setCustomerId(1L);
        orders1.setProductName("Product 1");
        orders1.setProductCount(2);
        orders1.setTotal(new BigDecimal("20.00"));
        orders1.setPaid(false);
        orders1.setCancelled(false);

        Orders orders2 = new Orders();
        orders2.setCustomerId(2L);
        orders2.setProductName("Product 2");
        orders2.setProductCount(3);
        orders2.setTotal(new BigDecimal("30.00"));
        orders2.setPaid(true);
        orders2.setCancelled(false);


        ordersDao.save(orders1);
        ordersDao.save(orders2);


        List<Orders> customerOrders = ordersDao.findAllByCustomerId(1L);


        assertEquals(1, customerOrders.size());
        assertEquals(customerOrders.get(0).getId(), orders1.getId());
        assertFalse(customerOrders.contains(orders2));
    }

    @Test
    public void testTruncate() {

        Orders orders1 = new Orders();
        orders1.setCustomerId(1L);
        orders1.setProductName("Product 1");
        orders1.setProductCount(2);
        orders1.setTotal(new BigDecimal("20.00"));
        orders1.setPaid(false);
        orders1.setCancelled(false);

        Orders orders2 = new Orders();
        orders2.setCustomerId(2L);
        orders2.setProductName("Product 2");
        orders2.setProductCount(3);
        orders2.setTotal(new BigDecimal("30.00"));
        orders2.setPaid(true);
        orders2.setCancelled(false);


        ordersDao.save(orders1);
        ordersDao.save(orders2);


        ordersDao.truncate();


        assertEquals(0, ordersDao.findAll().size());
    }

}