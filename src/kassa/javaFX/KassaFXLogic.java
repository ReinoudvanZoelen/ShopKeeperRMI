package kassa.javaFX;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.IProductBeheer;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import kassa.RMI.ProductNotificationListener;

import java.rmi.RemoteException;
import java.util.List;

public class KassaFXLogic {

    private IProductBeheer productBeheer;
    private IKlantBeheer klantBeheer;
    private ProductNotificationListener pnl;

    public KassaFXLogic(IProductBeheer productBeheer,
                        IKlantBeheer klantBeheer,
                        ProductNotificationListener pnl) {
        this.productBeheer = productBeheer;
        this.klantBeheer = klantBeheer;
        this.pnl = pnl;
    }

    public Klant getKlant(String nfccode) throws RemoteException {
        return klantBeheer.getKlant(nfccode);
    }

    public boolean SaldoVerhogen(Klant klant, Double saldo) throws RemoteException {
        return klantBeheer.SaldoVerhogen(klant, saldo);
    }

    public List<Product> getProducten() throws RemoteException {
        return productBeheer.GetProducten();
    }

    public void placeVoorraadOrder(List<Product> producten) throws RemoteException {
        System.out.println("Starting Placing Voorraad Order...");
        productBeheer.VerwerkBestelling(producten);
    }

    public boolean placeKlantOrder(Bestelling bestelling) throws RemoteException {
        System.out.println("Starting Placing Klant Order...");
        // Eerst kijken of de betaling mag voltooien (genoeg saldo en voorraad)
        if (checkPaymentConditions(bestelling)) {
            System.out.println("Preconditions are positive, starting payment processing.");

            this.productBeheer.VerwerkBestelling(bestelling);
            this.klantBeheer.BetaalBestelling(bestelling);

            System.out.println("Payment processed successfully");
            return true;
        } else {
            System.out.println("Preconditions are negative, payment processing will not be started.");
            return false;
        }
    }

    private boolean checkPaymentConditions(Bestelling bestelling) throws RemoteException {
        // Controleren of er wel genoeg saldo is
        double totalPrice = calculateTotalPrice(bestelling.producten);
        boolean klantHasEnoughSaldo = totalPrice <= this.klantBeheer.getKlant(bestelling.klant.nfccode).saldo; // Klant opnieuw ophalen zodat het saldo geupdatet is
        System.out.println("Has enough saldo: " + klantHasEnoughSaldo);

        if (!klantHasEnoughSaldo) {
            new Alert(Alert.AlertType.INFORMATION, "De klant heeft onvoldoende saldo.", ButtonType.CLOSE).show();
        }

        // Check if all products are in stock for the required amount
        boolean productsAreInStock = hasStock(bestelling.producten);
        System.out.println("Has enough stock: " + productsAreInStock);

        if (!productsAreInStock) {
            new Alert(Alert.AlertType.INFORMATION, "De geselecteerde producten zijn niet op voorraad.", ButtonType.CLOSE).show();

        }

        return (klantHasEnoughSaldo && productsAreInStock);
    }

    private boolean hasStock(List<Product> producten) {

        boolean hasPlenty = true;

        for (Product product : producten) {
            int currentProductAmount = 0;
            for (Product product1 : producten) {
                if (product.id == product1.id) {
                    currentProductAmount++;
                }
            }

            System.out.println("Product " + product.naam + " has a supply of " + product.voorraad + " and appears in the other " + currentProductAmount + " of times.");
            if (currentProductAmount > product.voorraad) {
                System.out.println("Not enough stock, settings hasPlenty to false.");
                hasPlenty = false;
            }
        }

        return hasPlenty;
    }

    private double calculateTotalPrice(List<Product> producten) {
        double totalPrice = 0;

        for (Product p : producten) {
            totalPrice += p.prijs;
        }

        return totalPrice;
    }


}
