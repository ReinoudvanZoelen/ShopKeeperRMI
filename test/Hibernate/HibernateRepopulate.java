package Hibernate;

import Database.HibernateBestellingRepository;
import Database.HibernateKlantRepository;
import Database.HibernateProductRepository;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HibernateRepopulate {

    @Test
    @Disabled
    public void ResetDatabase() {
        System.out.println("Setting up the HibernateRepositories");
        HibernateProductRepository hiberProduct = new HibernateProductRepository();
        HibernateKlantRepository hiberKlant = new HibernateKlantRepository();
        HibernateBestellingRepository hiberBestelling = new HibernateBestellingRepository();
        System.out.println("Done settings up the HibernateRepositories.");


        System.out.println("Removing all existing items from the database.");
        for (Bestelling bestelling : hiberBestelling.findAll()) {
            hiberBestelling.delete(bestelling);
        }

        for (Product product : hiberProduct.findAll()) {
            hiberProduct.delete(product);
        }

        for (Klant klant : hiberKlant.findAll()) {
            hiberKlant.delete(klant);
        }
        System.out.println("Done removing all existing items from the database.");


        Klant klant1 = new Klant("Reinoud van Zoelen", 100d, "zoel");
        Klant klant2 = new Klant("Niels Werkman", 10d, "werk");
        Klant klant3 = new Klant("Bono IJpelaar", 0d, "bono");

        Product product1 = new Product("Fanta regular", 2d, 5);
        Product product2 = new Product("Cola Zero", 2.30d, 2);

        System.out.println("Writing new items to the database");

        hiberKlant.create(klant1);
        hiberKlant.create(klant2);
        hiberKlant.create(klant3);

        hiberProduct.create(product1);
        hiberProduct.create(product2);

        List<Product> productset1 = hiberProduct.findAll();

        Bestelling bestelling = new Bestelling(klant1, productset1);

        hiberBestelling.create(bestelling);

        System.out.println("Done writing new items to the database.");
    }
}
