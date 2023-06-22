package wipb.ee.jspdemo.web.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wipb.ee.jspdemo.web.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = new UserDao();
    }

    @AfterEach
    public void tearDown() {
        userDao.truncate();
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setBalance(BigDecimal.valueOf(100));

        userDao.save(user);

        assertNotNull(user.getId());
        Optional<User> savedUser = userDao.findByLoginPassword(user.getLogin(), user.getPassword());
        assertTrue(savedUser.isPresent());
        assertEquals(user.getLogin(), savedUser.get().getLogin());
        assertEquals(user.getName(), savedUser.get().getName());
        assertEquals(user.getEmail(), savedUser.get().getEmail());
        assertEquals(user.getBalance(), savedUser.get().getBalance());
        assertEquals("customer", savedUser.get().getRole());
    }

    @Test
    public void testUpdatePassword() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setBalance(BigDecimal.valueOf(100));
        userDao.save(user);

        String newPassword = "newpassword";
        user.setPassword(newPassword);
        userDao.updatePassword(user);

        Optional<User> updatedUser = userDao.findByLoginPassword(user.getLogin(), newPassword);
        assertTrue(updatedUser.isPresent());
        assertEquals(user.getPassword(), updatedUser.get().getPassword());
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setBalance(BigDecimal.valueOf(100.0));
        userDao.save(user);

        userDao.delete(user.getId());

        Optional<User> deletedUser = userDao.findByLoginPassword(user.getLogin(), user.getPassword());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    public void testFindByLoginPassword() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setBalance(BigDecimal.valueOf(100));
        userDao.save(user);

        Optional<User> foundUser = userDao.findByLoginPassword(user.getLogin(), user.getPassword());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getLogin(), foundUser.get().getLogin());
        assertEquals(user.getName(), foundUser.get().getName());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        assertEquals(user.getBalance(), foundUser.get().getBalance());
        assertEquals(user.getPassword(), foundUser.get().getPassword());
    }

    @Test
    public void testFindByLoginPassword_NonExistentUser() {
        Optional<User> foundUser = userDao.findByLoginPassword("nonexistent", "password");
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindAll() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password1");
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        user1.setBalance(BigDecimal.valueOf(50.0));
        userDao.save(user1);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("password2");
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        user2.setBalance(BigDecimal.valueOf(100.0));
        userDao.save(user2);

        List<User> users = userDao.findAll();
        assertEquals(2, users.size());
        assertEquals(users.get(0).getId(),user1.getId());
        assertEquals(users.get(1).getId(),user2.getId());
    }
    @Test
    public void testTruncate() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password1");
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        user1.setBalance(BigDecimal.valueOf(50.0));
        userDao.save(user1);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("password2");
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        user2.setBalance(BigDecimal.valueOf(100.0));
        userDao.save(user2);


        // Wywołaj metodę truncate()
        userDao.truncate();

        // Sprawdź, czy baza danych jest pusta po wywołaniu truncate()
        assertEquals(0, userDao.findAll().size());
    }
}