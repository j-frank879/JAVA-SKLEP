package wipb.ee.jspdemo.web.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        user.setId(id);

        Long retrievedId = user.getId();

        assertEquals(id, retrievedId);
    }

    @Test
    public void testGetLogin() {
        String login = "example_login";
        user.setLogin(login);

        String retrievedLogin = user.getLogin();

        assertEquals(login, retrievedLogin);
    }

    @Test
    public void testGetPassword() {
        String password = "example_password";
        user.setPassword(password);

        String retrievedPassword = user.getPassword();

        assertEquals(password, retrievedPassword);
    }

    @Test
    public void testGetRole() {
        String role = "user_role";
        user.setRole(role);

        String retrievedRole = user.getRole();

        assertEquals(role, retrievedRole);
    }

    @Test
    public void testGetName() {
        String name = "Example User";
        user.setName(name);

        String retrievedName = user.getName();

        assertEquals(name, retrievedName);
    }

    @Test
    public void testGetEmail() {
        String email = "example@example.com";
        user.setEmail(email);

        String retrievedEmail = user.getEmail();

        assertEquals(email, retrievedEmail);
    }

    @Test
    public void testGetBalance() {
        BigDecimal balance = new BigDecimal("100.00");
        user.setBalance(balance);

        BigDecimal retrievedBalance = user.getBalance();

        assertEquals(balance, retrievedBalance);
    }

    @Test
    public void testSetId() {
        Long id = 1L;
        user.setId(id);

        Long retrievedId = user.getId();

        assertEquals(id, retrievedId);
    }

    @Test
    public void testSetLogin() {
        String login = "example_login";
        user.setLogin(login);

        String retrievedLogin = user.getLogin();

        assertEquals(login, retrievedLogin);
    }

    @Test
    public void testSetPassword() {
        String password = "example_password";
        user.setPassword(password);

        String retrievedPassword = user.getPassword();

        assertEquals(password, retrievedPassword);
    }

    @Test
    public void testSetRole() {
        String role = "user_role";
        user.setRole(role);

        String retrievedRole = user.getRole();

        assertEquals(role, retrievedRole);
    }

    @Test
    public void testSetName() {
        String name = "Example User";
        user.setName(name);

        String retrievedName = user.getName();

        assertEquals(name, retrievedName);
    }

    @Test
    public void testSetEmail() {
        String email = "example@example.com";
        user.setEmail(email);

        String retrievedEmail = user.getEmail();

        assertEquals(email, retrievedEmail);
    }

    @Test
    public void testSetBalance() {
        BigDecimal balance = new BigDecimal("100.00");
        user.setBalance(balance);

        BigDecimal retrievedBalance = user.getBalance();

        assertEquals(balance, retrievedBalance);
    }
}