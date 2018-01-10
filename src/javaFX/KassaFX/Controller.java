package javaFX.KassaFX;

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

    private Klant klant = null;
    private ObservableList<Product> queuedProducts = FXCollections.observableList(new ArrayList<Product>());

    @FXML
    public TextField textfield_NFCCode;

    @FXML
    public ListView listview_TeBestellen;

    @FXML
    public ListView listview_OpenBestellingenKlant;

    @FXML
    public Label label_CurrentKlant;

    @FXML
    protected void initialize() throws RemoteException {
        updateProductList();
        listview_OpenBestellingenKlant.setItems(queuedProducts);
    }

    public void NFCChanged() {
        System.out.println("NFC Changed. Content: " + textfield_NFCCode.getText());

        updateKlant();
    }

    private void updateKlant() {
        try {
            this.klant = Main.klantBeheer.getKlantByNFC(textfield_NFCCode.getText());
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

    public void ProductListDoubleclicked() {
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
        } else {
            ArrayList<Product> producten = new ArrayList<>(this.queuedProducts);

            if (this.klant == null) {
                new Alert(Alert.AlertType.INFORMATION, "Er is geen klant geselecteerd.", ButtonType.CLOSE).show();
            } else {
                try {
                    Betalen(producten);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
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

    private void Betalen(ArrayList<Product> producten) throws RemoteException {
        System.out.println("Betalen, pannekoek!");

        if (checkPrepaymentConditions(producten)) {
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

    }

    private boolean checkPrepaymentConditions(ArrayList<Product> producten) throws RemoteException {
        double totalPrice;

        // Controleren of er wel genoeg saldo is
        totalPrice = calculateTotalPrice(producten);
        boolean klantHasEnoughSaldo = totalPrice <= klant.saldo;
        System.out.println("Has enough saldo: " + klantHasEnoughSaldo);

        // Voorraad controleren
        // Assume to be true, unless a product returns false
        boolean productsAreInStock = true;

        for (Product product : producten) {
            boolean productHasStock = productInStock(product, producten);
            if (!productHasStock) {
                productsAreInStock = false;
                System.out.println("Product " + product.naam + " is not in stock for the requested amount.");
            }
        }
        System.out.println("Has enough stock: " + productsAreInStock);

        return (klantHasEnoughSaldo && productsAreInStock);
    }

    private boolean processPayment(ArrayList<Product> producten) throws RemoteException {
        // Bedrag afschrijven
        boolean klantHasPaid = Main.klantBeheer.SaldoVerlagen(klant, calculateTotalPrice(producten));

        System.out.println("Has paid: " + klantHasPaid);

        // Voorraad bijwerken
        boolean productStockIsUpdated = removeProductsfromStock(producten);

        System.out.println("Productstock has been updated: " + productStockIsUpdated);

        // Refetch the customer so the saldo is updated
        this.updateKlant();
        this.updateProductList();
        this.emptyQueuedOrder();

        // Resultaat retourneren
        if (klantHasPaid && productStockIsUpdated) {
            System.out.println("Payment successfully processed");
            return true;
        }

        System.out.println("Something went wrong during payment processing.");
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

        int amountInStock = Main.productBeheer.GetProductVoorraad(product);

        return amountInStock >= amountOfProduct;
    }

    private boolean removeProductsfromStock(ArrayList<Product> producten) throws RemoteException {
        boolean stockUpdated = true;

        for (Product product : producten) {
            boolean stockIsEdited = Main.productBeheer.RemoveItemFromStockOnce(product);
            if (!stockIsEdited) {
                stockUpdated = false;
            }
        }

        return stockUpdated;
    }
}
