package Hibernate;

import Database.Database;
import Database.HibernateBestellingRepository;
import Database.HibernateKlantRepository;
import Database.HibernateProductRepository;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;
import kassa.RMI.KlantDataset;
import kassa.RMI.ProductDataset;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class HibernateConnectionVerification {

    @Test
    public void test_ConnectionToDatabase() {
        boolean open = Database.SESSION.isOpen();

        boolean productDataExists = (new ProductDataset().getProductBeheer() != null);
        boolean klantDataExists = (new KlantDataset().getKlantBeheer() != null);

        Assert.assertTrue(open);
        Assert.assertTrue(productDataExists);
        Assert.assertTrue(klantDataExists);
    }

    @Test
    public void test_ReadDatabase() {
        // Arrange
        System.out.println("Setting up the HibernateRepositories");
        HibernateProductRepository hiberProduct = new HibernateProductRepository();
        HibernateKlantRepository hiberKlant = new HibernateKlantRepository();
        HibernateBestellingRepository hiberBestelling = new HibernateBestellingRepository();
        System.out.println("Done settings up the HibernateRepositories.");

        int productenAantal = 0;
        int klantenAantal = 0;
        int bestellingenAantal = 0;

        // Act
        System.out.println("Looking for Product...");
        for (Product p : hiberProduct.findAll()) {
            productenAantal++;
            System.out.println(p.toString());
        }

        System.out.println("Looking for Klant...");
        for (Klant k : hiberKlant.findAll()) {
            klantenAantal++;
            System.out.println(k.toString());
        }

        System.out.println("Looking for Bestelling...");
        for (Bestelling b : hiberBestelling.findAll()) {
            bestellingenAantal++;
            System.out.println(b.toString());
        }

        // Assert
        Assert.assertTrue(productenAantal > 0);
        Assert.assertTrue(klantenAantal > 0);
        Assert.assertTrue(bestellingenAantal > 0);
    }
}
