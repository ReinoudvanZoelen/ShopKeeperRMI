package kassa.javaFX.KassaFX;

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
import java.util.List;

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
        if (listview_TeBestellen.getSelectionModel().getSelectedItems().get(0) instanceof Product) {
            Product product = (Product) listview_TeBestellen.getSelectionModel().getSelectedItems().get(0);
            queuedProducts.add(product);
        }
    }

    public void ConfirmOrderClicked() {
        System.out.println("Confirm order clicked");
        if (queuedProducts.size() == 0) {
            System.out.println("No products found.");
            new Alert(Alert.AlertType.INFORMATION, "Er zijn geen producten geselecteerd.", ButtonType.CLOSE).show();
        } else if (this.klant == null) {
            System.out.println("No klant found.");
            new Alert(Alert.AlertType.INFORMATION, "Er is geen klant geselecteerd.", ButtonType.CLOSE).show();
        } else {
            ArrayList<Product> producten = new ArrayList<>(this.queuedProducts);

            try {
                System.out.println("Placing order...");
                Bestelling bestelling = new Bestelling(klant, producten);
                System.out.println("Bestelling to be ordered: " + bestelling);
                placeOrder(bestelling);
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
            label_CurrentKlant.textProperty().setValue("Geselecteerde klant: " + this.klant.name + ", € " + klant.saldo);
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

    private void placeOrder(Bestelling bestelling) throws RemoteException {
        // Eerst kijken of de betaling mag voltooien (genoeg saldo en voorraad)
        if (checkPaymentConditions(bestelling.producten)) {
            System.out.println("Preconditions are positive, starting payment processing.");

            Main.productBeheer.VerwerkBetestelling(bestelling);
            Main.klantBeheer.BetaalBestelling(bestelling);

            this.updateKlant();
            this.updateProductList();
            this.emptyQueuedOrder();

            System.out.println("Payment processed successfully");
        } else {
            System.out.println("Preconditions are negative, payment processing will not be started.");
        }
    }

    private boolean checkPaymentConditions(List<Product> teBestellenProducten) throws RemoteException {
        // Controleren of er wel genoeg saldo is
        double totalPrice = calculateTotalPrice(teBestellenProducten);
        boolean klantHasEnoughSaldo = totalPrice <= Main.klantBeheer.getKlant(this.klant.nfccode).saldo; // Klant opnieuw ophalen zodat het saldo geupdatet is
        System.out.println("Has enough saldo: " + klantHasEnoughSaldo);

        if (!klantHasEnoughSaldo) {
            new Alert(Alert.AlertType.INFORMATION, "De klant heeft onvoldoende saldo.", ButtonType.CLOSE).show();
        }

        // Check if all products are in stock for the required amount
        boolean productsAreInStock = hasStock(teBestellenProducten);
        System.out.println("Has enough stock: " + productsAreInStock);

        if (!productsAreInStock) {
            new Alert(Alert.AlertType.INFORMATION, "De geselecteerde producten zijn niet op voorraad.", ButtonType.CLOSE).show();
        }

        return (klantHasEnoughSaldo && productsAreInStock);
    }

    private boolean hasStock(List<Product> producten) throws RemoteException {

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
