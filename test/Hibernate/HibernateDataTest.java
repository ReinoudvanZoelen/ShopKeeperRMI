package Hibernate;

import Database.HibernateProductRepository;
import _shared.Models.Product;
import org.junit.*;
import org.junit.jupiter.api.Test;

public class HibernateDataTest {

    @Test
    public void testDummy() {
        Assert.assertTrue(true);
    }

    @Test
    public void testConnectionToDatabaseNiels() {
        Product product0 = new Product(0, "Cola", 2.0d);
        Product product1 = new Product(1, "Fanta", 4.5d);

        HibernateProductRepository hpr = new HibernateProductRepository();

        hpr.create(product0);
        hpr.create(product1);


    }

}
