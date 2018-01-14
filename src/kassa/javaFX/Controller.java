package kassa.javaFX;

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

    //region Order management
    @FXML
    public Label label_CurrentKlant;
    @FXML
    public TextField textfield_NFCCode;
    @FXML
    public ListView<Product> listview_ProductenVoorKlant;
    @FXML
    public ListView<Product> listview_OpenBestellingKlant;
    @FXML
    public Button button_ConfirmOpenKlantBestelling;
    @FXML
    public Button button_CancelOpenKlantBestelling;
    @FXML
    public ChoiceBox<Integer> choiceBox_OpwaardeerMogelijkheden;
    @FXML
    public Button button_Opwaarderen;
    private ObservableList<Product> queuedKlantBestellingProducts = FXCollections.observableList(new ArrayList<Product>());
    private ObservableList<Integer> opwaardeerOptions = FXCollections.observableList(new ArrayList<Integer>());
    // endregion

    // region Product management
    @FXML
    public ListView<Product> listview_ProductenVoorVoorraad;
    @FXML
    public ListView<Product> listview_OpenBestellingVoorraad;
    @FXML
    public Button button_ConfirmOpenVoorraadBestelling;
    @FXML
    public Button button_CancelOpenVoorraadBestelling;
    @FXML
    public ListView listview_OpenVoorraadBestellingenBijLeverancier;
    private ObservableList<Product> queuedVoorraadBestellingProducts = FXCollections.observableList(new ArrayList<Product>());
    // endregion

    private Klant klant = null;

    @FXML
    protected void initialize() throws RemoteException {
        listview_ProductenVoorKlant.setItems(queuedKlantBestellingProducts);
        listview_ProductenVoorVoorraad.setItems(queuedVoorraadBestellingProducts);
        updateProductList();
        choiceBox_OpwaardeerMogelijkheden.setItems(opwaardeerOptions);
        opwaardeerOptions.add(5);
        opwaardeerOptions.add(10);
        opwaardeerOptions.add(20);
        opwaardeerOptions.add(50);
        opwaardeerOptions.add(100);
        opwaardeerOptions.add(150);

    }

    public void ProductenKlantBestellingDoubleClicked() {
        System.out.println("Product list clicked");
        if (listview_ProductenVoorKlant.getSelectionModel().getSelectedItems().get(0) != null) {
            Product product = listview_ProductenVoorKlant.getSelectionModel().getSelectedItems().get(0);
            queuedKlantBestellingProducts.add(product);
        }
    }

    public void ConfirmKlantOrderClicked() {
        if (queuedKlantBestellingProducts.size() == 0) {
            new Alert(Alert.AlertType.INFORMATION, "Er zijn geen producten geselecteerd.", ButtonType.CLOSE).show();
        } else if (this.klant == null) {
            new Alert(Alert.AlertType.INFORMATION, "Er is geen klant geselecteerd.", ButtonType.CLOSE).show();
        } else {
            ArrayList<Product> producten = new ArrayList<>(this.queuedKlantBestellingProducts);

            try {
                System.out.println("Placing order...");
                Bestelling bestelling = new Bestelling(klant, producten);
                System.out.println("Bestelling to be ordered: " + bestelling);
                placeKlantOrder(bestelling);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    public void ProductenVoorraadBestellingDoubleClicked() {
        System.out.println("Product list clicked");
        Product product;
        if (listview_ProductenVoorVoorraad.getSelectionModel().getSelectedItems().get(0) != null) {
            product = listview_ProductenVoorVoorraad.getSelectionModel().getSelectedItems().get(0);
            queuedVoorraadBestellingProducts.add(product);
        }
    }

    public void ConfirmVoorraadOrderClicked() {
        System.out.println("Confirm order clicked");
        if (queuedVoorraadBestellingProducts.size() == 0) {
            System.out.println("No products found.");
            new Alert(Alert.AlertType.INFORMATION, "Er zijn geen producten geselecteerd om te verkopen.", ButtonType.CLOSE).show();
        } else if (this.klant == null) {
            new Alert(Alert.AlertType.INFORMATION, "Er is geen klant geselecteerd.", ButtonType.CLOSE).show();
        } else {
            ArrayList<Product> producten = new ArrayList<>(this.queuedKlantBestellingProducts);

            try {
                System.out.println("Placing order...");
                Bestelling bestelling = new Bestelling(klant, producten);
                System.out.println("Bestelling to be ordered for Voorraad: " + bestelling);
                placeVoorraadOrder(bestelling);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    public void OpwaarderenClicked() throws RemoteException {
        if (this.klant == null) {
            System.out.println("No klant found.");
            new Alert(Alert.AlertType.INFORMATION, "Er is geen klant geselecteerd.", ButtonType.CLOSE).show();
        } else if (choiceBox_OpwaardeerMogelijkheden.getSelectionModel().getSelectedItem() instanceof Integer) {
            int value = choiceBox_OpwaardeerMogelijkheden.getSelectionModel().getSelectedItem();
            Main.klantBeheer.SaldoVerhogen(klant, new Double(value));
            this.updateKlant();
        } else {
            System.out.println("No value was selected");
        }
    }

    @FXML
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
        listview_ProductenVoorKlant.setItems(FXCollections.observableList(Main.productBeheer.GetProducten()));
        listview_ProductenVoorVoorraad.setItems(FXCollections.observableList(Main.productBeheer.GetProducten()));
    }

    @FXML
    private void emptyKlantQueuedOrder() {
        queuedKlantBestellingProducts.remove(0, queuedKlantBestellingProducts.size());
    }

    @FXML
    private void emptyVoorraadQueuedOrder() {
        queuedKlantBestellingProducts.remove(0, queuedKlantBestellingProducts.size());
    }

    private void placeVoorraadOrder(Bestelling bestelling) throws RemoteException {
        // TODO: Implement vooraad
    }

    private void placeKlantOrder(Bestelling bestelling) throws RemoteException {
        // Eerst kijken of de betaling mag voltooien (genoeg saldo en voorraad)
        if (checkPaymentConditions(bestelling.producten)) {
            System.out.println("Preconditions are positive, starting payment processing.");

            Main.productBeheer.VerwerkBetestelling(bestelling);
            Main.klantBeheer.BetaalBestelling(bestelling);

            this.updateKlant();
            this.updateProductList();
            this.emptyKlantQueuedOrder();

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
