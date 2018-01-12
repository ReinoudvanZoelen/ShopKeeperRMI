package javaFX.KassaFX;

import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kassa.Main;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controller {

    @FXML
    public TextField textfield_NFCCode;
    @FXML
    public ListView listview_TeBestellen;
    @FXML
    public ListView listview_OpenBestellingenKlant;
    @FXML
    public Label label_CurrentKlant;
    private Klant klant = null;
    private ObservableList<Product> queuedProducts = FXCollections.observableList(new ArrayList<Product>());

    @FXML
    protected void initialize() throws RemoteException {
        updateProductList();
        listview_OpenBestellingenKlant.setItems(queuedProducts);
    }

    public void ProductListDoubleClicked() {
        System.out.println("Product list clicked");
        Product product;
        if (listview_TeBestellen.getSelectionModel().getSelectedItems().get(0) instanceof Product) {
            product = (Product) listview_TeBestellen.getSelectionModel().getSelectedItems().get(0);
            queuedProducts.add(product);
        }
    }

    public void ConfirmOrderClicked() {
        System.out.println("Confirm order clicked");
        if (queuedProducts.size() == 0) {
            System.out.println("No products found.");
            new Alert(Alert.AlertType.INFORMATION, "Er zijn geen producten geselecteerd om te verkopen.", ButtonType.CLOSE).show();
        } else if (this.klant == null) {
            new Alert(Alert.AlertType.INFORMATION, "Er is geen klant geselecteerd.", ButtonType.CLOSE).show();
        } else {
            ArrayList<Product> producten = new ArrayList<Product>(this.queuedProducts);

            try {
                System.out.println("Placing order...");
                placeOrder(klant, producten);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    public void CancelOrderClicked() {
        System.out.println("Cancel order clicked");
        emptyQueuedOrder();
    }

    public void KlantenClicked() {
        System.out.println("Klanten clicked");
        try {
            Main.StartKlanten();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProductenClicked() {
        System.out.println("Producten clicked");
        try {
            Main.StartProducten();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void NFCChanged() {
        System.out.println("NFC Changed. Content: " + textfield_NFCCode.getText());

        updateKlant();
    }

    private void updateKlant() {
        try {
            this.klant = Main.klantBeheer.getKlant(textfield_NFCCode.getText());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (klant != null) {
            System.out.println("Klant gevonden: " + klant.toString());
            label_CurrentKlant.textProperty().setValue("Geselecteerde klant: " + this.klant.name);
        } else {
            label_CurrentKlant.textProperty().setValue("Geen klant geselecteerd.");
        }
    }

    private void updateProductList() throws RemoteException {
        listview_TeBestellen.setItems(FXCollections.observableList(Main.productBeheer.GetProducten()));
    }

    private void emptyQueuedOrder() {
        queuedProducts.remove(0, queuedProducts.size());
    }

    private void placeOrder(Klant klant, ArrayList<Product> producten) throws RemoteException {
        // Eerst kijken of de betaling mag voltooien (genoeg saldo en voorraad)
        if (checkPaymentConditions(producten)) {
            System.out.println("Preconditions are positive, starting payment processing.");
            boolean ppay = processPayment(producten);
            if (ppay) {
                System.out.println("Payment processed successfully");
            } else {
                System.out.println("Payment not processed successfully.");
            }
        } else {
            System.out.println("Preconditions are negative, payment processing will not be started.");
        }

        Bestelling bestelling = new Bestelling(klant, producten);

        Main.productBeheer.VerwerkBetestelling(bestelling);
        Main.klantBeheer.BetaalBestelling(bestelling);
    }

    private boolean checkPaymentConditions(ArrayList<Product> teBestellenProducten) throws RemoteException {
        // Controleren of er wel genoeg saldo is
        double totalPrice = calculateTotalPrice(teBestellenProducten);
        boolean klantHasEnoughSaldo = totalPrice <= Main.klantBeheer.getKlant(this.klant.nfccode).saldo; // Klant opnieuw ophalen zodat het saldo geupdatet is
        System.out.println("Has enough saldo: " + klantHasEnoughSaldo);

        // Check if all products are in stock for the required amount
        boolean productsAreInStock = hasStock(teBestellenProducten);
        System.out.println("Has enough stock: " + productsAreInStock);

        return (klantHasEnoughSaldo && productsAreInStock);
    }

    private boolean hasStock(ArrayList<Product> producten) throws RemoteException {
        for (Product product : producten) {
            boolean productHasStock = productInStock(product, producten);
            if (!productHasStock) {
                System.out.println("Product " + product.naam + " is not in stock for the requested amount.");
                return false;
            }
        }
        return true;
    }

    private boolean processPayment(ArrayList<Product> producten) throws RemoteException {
/*        // Bedrag afschrijven
        boolean klantHasPaid = Main.klantBeheer.SaldoVerlagen(klant, calculateTotalPrice(producten));

        System.out.println("Has paid: " + klantHasPaid);

        // Voorraad bijwerken
        boolean productStockIsUpdated = removeProductsfromStock(producten);

        System.out.println("Productstock has been updated: " + productStockIsUpdated);*/

// TODO: Call naar beiden servers maken voor het verwerken


        // Refetch the datasets so the values are updated
        this.updateKlant();
        this.updateProductList();
        this.emptyQueuedOrder();

        // Resultaat retourneren
        if (true/*klantHasPaid && productStockIsUpdated*/) {
            System.out.println("Payment successfully processed");
            return true;
        }

        System.out.println("Something went wrong during payment or stock processing.");
        return false;
    }

    private double calculateTotalPrice(ArrayList<Product> producten) {
        double totalPrice = 0;

        for (Product p : producten) {
            totalPrice += p.prijs;
        }

        return totalPrice;
    }

    private boolean productInStock(Product product, ArrayList<Product> teBestellen) throws RemoteException {
        int amountOfProduct = 0;

        for (Product p : teBestellen) {
            if (product.id == p.id) {
                amountOfProduct++;
            }
        }

        // Voorraad zo dicht mogelijk bij de vergelijking ophalen
        int amountInStock = Main.productBeheer.GetProductVoorraad(product);

        return amountInStock >= amountOfProduct;
    }

    private boolean removeProductsfromStock(ArrayList<Product> producten) throws RemoteException {
        boolean stockUpdated = true;

/*      TODO
        for (Product product : producten) {
            boolean stockIsEdited = Main.productBeheer.RemoveItemFromStockOnce(product);
            if (!stockIsEdited) {
                stockUpdated = false;
            }
        }*/

        return stockUpdated;
    }
}
