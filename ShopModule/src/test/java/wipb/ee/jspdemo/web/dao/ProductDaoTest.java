package wipb.ee.jspdemo.web.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wipb.ee.jspdemo.web.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoTest {
    private ProductDao productDao;

    @BeforeEach
    public void setUp() {
        // Inicjalizacja przed każdym testem
        productDao = new ProductDao();
        // Tutaj możesz ewentualnie zainicjalizować bazę danych testową
    }

    @AfterEach
    public void tearDown() {
        // Sprzątanie po każdym teście
        productDao.truncate(); // Usuwa wszystkie produkty z bazy danych
    }

    @Test
    public void testSave() {
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10));

        productDao.save(product);

        assertNotNull(product.getId()); // Upewniamy się, że produkt otrzymał przypisane ID
        Optional<Product> savedProduct = productDao.find(product.getId());
        assertTrue(savedProduct.isPresent()); // Produkt powinien być zapisany w bazie danych
        assertEquals(product.getName(), savedProduct.get().getName()); // Sprawdzamy zgodność nazwy
        assertEquals(product.getPrice(), savedProduct.get().getPrice()); // Sprawdzamy zgodność ceny
    }

    @Test
    public void testUpdate() {
        Product product = new Product();
        product.setName("Product 2");
        product.setPrice(BigDecimal.valueOf(20));
        productDao.save(product);

        product.setName("Updated Product");
        product.setPrice(BigDecimal.valueOf(30));
        productDao.update(product);

        Optional<Product> updatedProduct = productDao.find(product.getId());
        assertTrue(updatedProduct.isPresent());
        assertEquals(product.getName(), updatedProduct.get().getName()); // Sprawdzamy, czy nazwa została zaktualizowana
        assertEquals(product.getPrice(), updatedProduct.get().getPrice()); // Sprawdzamy, czy cena została zaktualizowana
    }

    @Test
    public void testDelete() {
        Product product = new Product();
        product.setName("Product 3");
        product.setPrice(BigDecimal.valueOf(9.99));
        productDao.save(product);

        productDao.delete(product);

        Optional<Product> deletedProduct = productDao.find(product.getId());
        assertFalse(deletedProduct.isPresent()); // Produkt powinien zostać usunięty z bazy danych
    }

    @Test
    public void testFindById() {
        Product product = new Product();
        product.setName("Product 4");
        product.setPrice(BigDecimal.valueOf(10));
        productDao.save(product);

        Optional<Product> foundProduct = productDao.find(product.getId());
        assertTrue(foundProduct.isPresent()); // Produkt powinien zostać znaleziony w bazie danych
        assertEquals(product.getName(), foundProduct.get().getName()); // Sprawdzamy zgodność nazwy
        assertEquals(product.getPrice(), foundProduct.get().getPrice()); // Sprawdzamy zgodność ceny
    }

    @Test
    public void testFindByName() {
        Product product = new Product();
        product.setName("Product 5");
        product.setPrice(BigDecimal.valueOf(10));
        productDao.save(product);

        Optional<Product> foundProduct = productDao.find(product.getName());
        assertTrue(foundProduct.isPresent()); // Produkt powinien zostać znaleziony w bazie danych
        assertEquals(product.getName(), foundProduct.get().getName()); // Sprawdzamy zgodność nazwy
        assertEquals(product.getPrice(), foundProduct.get().getPrice()); // Sprawdzamy zgodność ceny
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product();
        product1.setName("Product 6");
        product1.setPrice(BigDecimal.valueOf(9.99));
        productDao.save(product1);

        Product product2 = new Product();
        product2.setName("Product 7");
        product2.setPrice(BigDecimal.valueOf(19.99));
        productDao.save(product2);

        List<Product> products = productDao.findAll();
        assertEquals(2, products.size()); // Sprawdzamy, czy znaleziono 2 produkty
        assertEquals(product1.getName(), products.get(0).getName()); // Sprawdzamy zgodność nazwy pierwszego produktu
        assertEquals(product2.getName(), products.get(1).getName()); // Sprawdzamy zgodność nazwy drugiego produktu
    }
    @Test
    public void testTruncate() {
        Product product1 = new Product();
        product1.setName("Product 6");
        product1.setPrice(BigDecimal.valueOf(9.99));
        productDao.save(product1);

        Product product2 = new Product();
        product2.setName("Product 7");
        product2.setPrice(BigDecimal.valueOf(19.99));
        productDao.save(product2);

        // Wywołaj metodę truncate()
        productDao.truncate();

        // Sprawdź, czy baza danych jest pusta po wywołaniu truncate()
        assertEquals(0, productDao.findAll().size());
    }
}