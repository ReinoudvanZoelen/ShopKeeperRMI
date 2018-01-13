package Hibernate;

import Database.Database;
import Database.HibernateBestellingRepository;
import Database.HibernateKlantRepository;
import Database.HibernateProductRepository;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;
import kassa.Database.RMI.KlantDataset;
import kassa.Database.RMI.ProductDataset;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

public class HibernateTests {

    @Test
    public void test_ConnectionToDatabase() {
        boolean open = Database.SESSION.isOpen();

        Assert.assertTrue(open);
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
            System.out.println("Bestelling found!");
            System.out.println(b.toString());
        }
    }


    @Test
    public void test_ProductPayment() {
        System.out.println("Setting up the HibernateRepositories");
        HibernateProductRepository hiberProduct = new HibernateProductRepository();
        HibernateKlantRepository hiberKlant = new HibernateKlantRepository();
        HibernateBestellingRepository hiberBestelling = new HibernateBestellingRepository();
        System.out.println("Done settings up the HibernateRepositories.");

        Bestelling bestelling = new Bestelling(hiberKlant.findAll().get(0), hiberProduct.findAll());
        System.out.println("====================================");
        System.out.println(bestelling.klant);
        System.out.println(bestelling.producten);
        System.out.println("====================================");
        try {
            new ProductDataset().getProductBeheer().VerwerkBetestelling(bestelling);
            new KlantDataset().getKlantBeheer().BetaalBestelling(bestelling);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("====================================");
        System.out.println(hiberKlant.findAll().get(0));
    }
}
