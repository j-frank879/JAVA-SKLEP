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
        // Inicjalizacja obiektu ordersDao i inne operacje przygotowujące do testów
        ordersDao = new OrdersDao();
        // ...
    }
    @AfterEach
    public void tearDown() {
        // Wywołanie metody truncate() po zakończeniu testów
        ordersDao.truncate();
    }
    @Test
    public void testSave() {
        // Przygotowanie testowych danych
        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setProductName("Product 1");
        orders.setProductCount(2);
        orders.setTotal(BigDecimal.valueOf(20));
        orders.setPaid(false);
        orders.setCancelled(false);

        // Wywołanie metody save()
        ordersDao.save(orders);

        // Sprawdzenie, czy zamówienie zostało poprawnie zapisane
        assertNotNull(orders.getId());

        // Pobranie zamówienia z bazy danych
        Optional<Orders> savedOrders = ordersDao.find(orders.getId());

        // Sprawdzenie, czy pobrane zamówienie ma poprawne dane
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
        // Przygotowanie testowych danych
        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setProductName("Product 1");
        orders.setProductCount(2);
        orders.setTotal(new BigDecimal("20.00"));
        orders.setPaid(false);
        orders.setCancelled(false);

        // Dodanie zamówienia do bazy danych
        ordersDao.save(orders);

        // Wywołanie metody cancel()
        ordersDao.cancel(orders.getId());

        // Pobranie zaktualizowanego zamówienia z bazy danych
        Optional<Orders> cancelledOrders = ordersDao.find(orders.getId());

        // Sprawdzenie, czy zamówienie zostało poprawnie anulowane
        assertTrue(cancelledOrders.isPresent());
        assertTrue(cancelledOrders.get().isCancelled());
    }

    @Test
    public void testPay() {
        // Przygotowanie testowych danych
        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setProductName("Product 1");
        orders.setProductCount(2);
        orders.setTotal(new BigDecimal("20.00"));
        orders.setPaid(false);
        orders.setCancelled(false);

        // Dodanie zamówienia do bazy danych
        ordersDao.save(orders);

        // Wywołanie metody pay()
        ordersDao.pay(orders.getId());

        // Pobranie zaktualizowanego zamówienia z bazy danych
        Optional<Orders> paidOrders = ordersDao.find(orders.getId());

        // Sprawdzenie, czy zamówienie zostało poprawnie opłacone
        assertTrue(paidOrders.isPresent());
        assertTrue(paidOrders.get().isPaid());
    }

    @Test
    public void testFind() {
        // Przygotowanie testowych danych
        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setProductName("Product 1");
        orders.setProductCount(2);
        orders.setTotal(BigDecimal.valueOf(20));
        orders.setPaid(false);
        orders.setCancelled(false);

        // Dodanie zamówienia do bazy danych
        ordersDao.save(orders);

        // Wywołanie metody find()
        Optional<Orders> retrievedOrders = ordersDao.find(orders.getId());

        // Sprawdzenie, czy zamówienie zostało poprawnie pobrane
        assertTrue(retrievedOrders.isPresent());
        assertEquals(orders.getId(), retrievedOrders.get().getId());
        assertEquals(orders.getCustomerId(), retrievedOrders.get().getCustomerId());
        assertEquals(orders.getProductName(), retrievedOrders.get().getProductName());
        assertEquals(orders.getProductCount(), retrievedOrders.get().getProductCount());
        assertEquals(orders.getTotal(),retrievedOrders.get().getTotal());
        assertEquals(orders.isPaid(), retrievedOrders.get().isPaid());
        assertEquals(orders.isCancelled(), retrievedOrders.get().isCancelled());
    }

    @Test
    public void testFindAll() {
        // Przygotowanie testowych danych
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



        // Wywołanie metody findAll()
        List<Orders> ordersList = ordersDao.findAll();

        // Sprawdzenie, czy wszystkie zamówienia zostały pobrane
        assertEquals(2, ordersList.size());
        assertEquals(orders1.getId(),ordersList.get(0).getId());
        assertEquals(orders2.getId(),ordersList.get(1).getId());
    }

    @Test
    public void testFindAllNotCancelled() {
        // Przygotowanie testowych danych
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
        // Dodanie zamówień do bazy danych



        // Wywołanie metody findAllNotCancelled()
        List<Orders> notCancelledOrders = ordersDao.findAllNotCancelled();

        // Sprawdzenie, czy pobrane zamówienia nie zawierają anulowanych zamówień
        assertEquals(1, notCancelledOrders.size());
        assertFalse(notCancelledOrders.get(0).isCancelled());
        assertEquals(notCancelledOrders.get(0).getId(),orders1.getId());
    }

    @Test
    public void testFindAllByCustomerId() {
        // Przygotowanie testowych danych
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

        // Dodanie zamówień do bazy danych
        ordersDao.save(orders1);
        ordersDao.save(orders2);

        // Wywołanie metody findAllByCustomerId()
        List<Orders> customerOrders = ordersDao.findAllByCustomerId(1L);

        // Sprawdzenie, czy pobrane zamówienia należą do określonego klienta
        assertEquals(1, customerOrders.size());
        assertEquals(customerOrders.get(0).getId(),orders1.getId());
        assertFalse(customerOrders.contains(orders2));
    }

    @Test
    public void testTruncate() {
        // Przygotowanie testowych danych
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

        // Dodanie zamówień do bazy danych
        ordersDao.save(orders1);
        ordersDao.save(orders2);

        // Wywołaj metodę truncate()
        ordersDao.truncate();

        // Sprawdź, czy baza danych jest pusta po wywołaniu truncate()
        assertEquals(0, ordersDao.findAll().size());
    }

}