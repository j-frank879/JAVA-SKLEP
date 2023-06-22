package wipb.ee.jspdemo.web.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        customer.setId(id);

        Long retrievedId = customer.getId();

        assertEquals(id, retrievedId);
    }

    @Test
    public void testGetName() {
        String name = "John Doe";
        customer.setName(name);

        String retrievedName = customer.getName();

        assertEquals(name, retrievedName);
    }

    @Test
    public void testGetBalance() {
        BigDecimal balance = new BigDecimal("1000.00");
        customer.setBalance(balance);

        BigDecimal retrievedBalance = customer.getBalance();

        assertEquals(balance, retrievedBalance);
    }

    @Test
    public void testGetEmail() {
        String email = "john.doe@example.com";
        customer.setEmail(email);

        String retrievedEmail = customer.getEmail();

        assertEquals(email, retrievedEmail);
    }

    @Test
    public void testGetUserId() {
        Long userId = 123L;
        customer.setUserId(userId);

        Long retrievedUserId = customer.getUserId();

        assertEquals(userId, retrievedUserId);
    }

    @Test
    public void testSetId() {
        Long id = 1L;
        customer.setId(id);

        Long retrievedId = customer.getId();

        assertEquals(id, retrievedId);
    }

    @Test
    public void testSetName() {
        String name = "John Doe";
        customer.setName(name);

        String retrievedName = customer.getName();

        assertEquals(name, retrievedName);
    }

    @Test
    public void testSetBalance() {
        BigDecimal balance = new BigDecimal("1000.00");
        customer.setBalance(balance);

        BigDecimal retrievedBalance = customer.getBalance();

        assertEquals(balance, retrievedBalance);
    }

    @Test
    public void testSetEmail() {
        String email = "john.doe@example.com";
        customer.setEmail(email);

        String retrievedEmail = customer.getEmail();

        assertEquals(email, retrievedEmail);
    }

    @Test
    public void testSetUserId() {
        Long userId = 123L;
        customer.setUserId(userId);

        Long retrievedUserId = customer.getUserId();

        assertEquals(userId, retrievedUserId);
    }
}