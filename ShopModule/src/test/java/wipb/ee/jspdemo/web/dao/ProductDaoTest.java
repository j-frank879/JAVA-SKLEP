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
        productDao = new ProductDao();
    }

    @AfterEach
    public void tearDown() {
        productDao.truncate(); 
    }

    @Test
    public void testSave() {
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10));

        productDao.save(product);

        assertNotNull(product.getId()); 
        Optional<Product> savedProduct = productDao.find(product.getId());
        assertTrue(savedProduct.isPresent()); 
        assertEquals(product.getName(), savedProduct.get().getName()); 
        assertEquals(product.getPrice(), savedProduct.get().getPrice()); 
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
        assertEquals(product.getName(), updatedProduct.get().getName()); 
        assertEquals(product.getPrice(), updatedProduct.get().getPrice()); 
    }

    @Test
    public void testDelete() {
        Product product = new Product();
        product.setName("Product 3");
        product.setPrice(BigDecimal.valueOf(9.99));
        productDao.save(product);

        productDao.delete(product);

        Optional<Product> deletedProduct = productDao.find(product.getId());
        assertFalse(deletedProduct.isPresent()); 
    }

    @Test
    public void testFindById() {
        Product product = new Product();
        product.setName("Product 4");
        product.setPrice(BigDecimal.valueOf(10));
        productDao.save(product);

        Optional<Product> foundProduct = productDao.find(product.getId());
        assertTrue(foundProduct.isPresent()); 
        assertEquals(product.getName(), foundProduct.get().getName()); 
        assertEquals(product.getPrice(), foundProduct.get().getPrice()); 
    }

    @Test
    public void testFindByName() {
        Product product = new Product();
        product.setName("Product 5");
        product.setPrice(BigDecimal.valueOf(10));
        productDao.save(product);

        Optional<Product> foundProduct = productDao.find(product.getName());
        assertTrue(foundProduct.isPresent()); 
        assertEquals(product.getName(), foundProduct.get().getName()); 
        assertEquals(product.getPrice(), foundProduct.get().getPrice()); 
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
        assertEquals(2, products.size()); 
        assertEquals(product1.getName(), products.get(0).getName()); 
        assertEquals(product2.getName(), products.get(1).getName()); 
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

        
        productDao.truncate();

        
        assertEquals(0, productDao.findAll().size());
    }
}