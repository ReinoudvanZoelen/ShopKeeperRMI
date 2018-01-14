package Hibernate;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.IProductBeheer;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;
import kassa.RMI.ProductNotificationListener;
import kassa.javaFX.KassaFXLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class KassaFXLogicTest {
    static IProductBeheer pb;
    static IKlantBeheer kb;
    static ProductNotificationListener pnl;

    @Before
    public void executedBeforeEach() throws RemoteException {
        pb = new IProductBeheer() {
            List<Bestelling> myBestellingen = new ArrayList<>();
            List<Product> myProducten = new ArrayList<>();

            @Override
            public List<Product> GetProducten() throws RemoteException {
                return myProducten;
            }

            @Override
            public void VerwerkBestelling(Bestelling bestelling) throws RemoteException {
                myBestellingen.add(bestelling);

                for (Product p : bestelling.producten) {
                    for (Product p2 : myProducten) {
                        if (p.id == p2.id) {
                            p2.voorraad--;
                        }
                    }
                }
            }

            @Override
            public void VerwerkBestelling(List<Product> producten) throws RemoteException {
                for (Product p : producten) {
                    for (Product p2 : this.myProducten) {
                        if (p.id == p2.id) {
                            p2.voorraad--;
                        }
                    }
                }
            }
        };
        kb = new IKlantBeheer() {
            List<Klant> myKlanten = new ArrayList<>();

            @Override
            public boolean SaldoVerhogen(Klant klant, Double hoeveelheid) throws RemoteException {
                double startsaldo = getKlant(klant.nfccode).saldo;
                double newSaldo = startsaldo + hoeveelheid;

                for (Klant k : myKlanten) {
                    if (klant.nfccode.equals(k.nfccode)) {
                        k.saldo = newSaldo;
                    }
                }

                return startsaldo > newSaldo;
            }

            @Override
            public boolean SaldoVerlagen(Klant klant, Double hoeveelheid) throws RemoteException {
                double startsaldo = getKlant(klant.nfccode).saldo;
                double newSaldo = startsaldo - hoeveelheid;

                for (Klant k : myKlanten) {
                    if (klant.nfccode.equals(k.nfccode)) {
                        k.saldo = newSaldo;
                    }
                }

                return startsaldo < newSaldo;
            }

            @Override
            public Klant getKlant(String NFC) throws RemoteException {
                for (Klant k : myKlanten) {
                    if (k.nfccode.equals(NFC)) {
                        return k;
                    }
                }
                return null;
            }

            @Override
            public List<Klant> getKlanten() throws RemoteException {
                return myKlanten;
            }

            @Override
            public void BetaalBestelling(Bestelling bestelling) throws RemoteException {

                double totalPrice = 0;

                for (Product p : bestelling.producten) {
                    totalPrice += p.prijs;
                }

                this.SaldoVerlagen(bestelling.klant, totalPrice);
            }
        };
        pnl = null;

        pb.GetProducten().add(new Product("Cola", 2d, 10));
        pb.GetProducten().add(new Product("Fanta", 1.5d, 5));
        kb.getKlanten().add(new Klant("Reinoud", 15d, "nfc"));
    }


    @Test
    public void test_PlaceKlantOrder() throws RemoteException {
        // Arrange
        KassaFXLogic logic = new KassaFXLogic(pb, kb, pnl);
        Klant klant = logic.getKlant("nfc");
        List<Product> producten = logic.getProducten();
        Bestelling bestelling = new Bestelling(klant, producten);
        int voorraad = pb.GetProducten().get(0).voorraad;

        // Act
        logic.placeKlantOrder(bestelling);

        // Assert
        Assert.assertEquals(7, pb.GetProducten().get(0).voorraad - 1);
    }

    @Test
    public void test_PlaceVoorraadOrder() throws RemoteException {
        // Arrange
        KassaFXLogic logic = new KassaFXLogic(pb, kb, pnl);
        Product product0 = pb.GetProducten().get(0);
        Product product1 = pb.GetProducten().get(1);

        // Act
        logic.placeVoorraadOrder(logic.getProducten());

        // Assert
        Assert.assertEquals(product0.voorraad, 8);
        Assert.assertEquals("Fanta", logic.getProducten().get(logic.getProducten().size() - 1).naam);
    }

    @Test
    public void test_SaldoVerhogen() throws RemoteException {
        // Arrange
        KassaFXLogic logic = new KassaFXLogic(pb, kb, pnl);
        Klant klant = logic.getKlant("nfc");

        // Act
        logic.SaldoVerhogen(klant, 5d);

        // Assert
        Assert.assertEquals(20d, logic.getKlant("nfc").saldo, 0.0001d);
    }


}
