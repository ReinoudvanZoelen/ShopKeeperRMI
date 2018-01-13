package Hibernate;

import Database.Database;
import Database.HibernateBestellingRepository;
import Database.HibernateKlantRepository;
import Database.HibernateProductRepository;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GenericDatabaseTests {
    @Before
    public void onlyOnce() {

    }

    @Test
    public void testDummy() {
        Assert.assertTrue(true);
    }

    @Test
    public void test_ConnectionToDatabase() {
        boolean open = Database.SESSION.isOpen();

        Assert.assertTrue(open);
    }

    @Test
    @Disabled
    public void test_PopulateDatabase() {
        System.out.println("Setting up the HibernateRepositories");
        HibernateProductRepository hiberProduct = new HibernateProductRepository();
        HibernateKlantRepository hiberKlant = new HibernateKlantRepository();
        HibernateBestellingRepository hiberBestelling = new HibernateBestellingRepository();
        System.out.println("Done settings up the HibernateRepositories.");

        Klant klant1 = new Klant("Reinoud van Zoelen", 100d, "zoel");
        Klant klant2 = new Klant("Niels Werkman", 10d, "werk");
        Klant klant3 = new Klant("Bono IJpelaar", 0d, "bono");

        Product product1 = new Product("Fanta regular", 2d, 5);
        Product product2 = new Product("Cola Zero", 2.30d, 2);


        hiberKlant.create(klant1);
        hiberKlant.create(klant2);
        hiberKlant.create(klant3);

        hiberProduct.create(product1);
        hiberProduct.create(product2);

        List<Product> productset1 = hiberProduct.findAll();

        Bestelling bestelling1 = new Bestelling(klant1, productset1);


        hiberBestelling.create(bestelling1);
    }

    @Test
    public void test_ReadDatabase() {
        System.out.println("Setting up the HibernateRepositories");
        HibernateProductRepository hiberProduct = new HibernateProductRepository();
        HibernateKlantRepository hiberKlant = new HibernateKlantRepository();
        HibernateBestellingRepository hiberBestelling = new HibernateBestellingRepository();
        System.out.println("Done settings up the HibernateRepositories.");

        System.out.println("Looking for Product...");
        for (Product p : hiberProduct.findAll()) {
            System.out.println(p.toString());
        }

        System.out.println("Looking for Klant...");
        for (Klant k : hiberKlant.findAll()) {
            System.out.println(k.toString());
        }

        System.out.println("Looking for Bestelling...");
        for (Bestelling b : hiberBestelling.findAll()) {
            System.out.println(b.toString());
        }
    }

}
