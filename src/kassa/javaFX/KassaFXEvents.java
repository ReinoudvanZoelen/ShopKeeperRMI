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

public class KassaFXEvents {
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

    private KassaFXLogic fxl;
    private Klant klant;

    @FXML
    protected void initialize() throws RemoteException {
        this.fxl = Main.logic;

        listview_OpenBestellingKlant.setItems(queuedKlantBestellingProducts);
        listview_OpenBestellingVoorraad.setItems(queuedVoorraadBestellingProducts);
        updateProducten();
        choiceBox_OpwaardeerMogelijkheden.setItems(opwaardeerOptions);
        opwaardeerOptions.add(5);
        opwaardeerOptions.add(10);
        opwaardeerOptions.add(20);
        opwaardeerOptions.add(50);
        opwaardeerOptions.add(100);
        opwaardeerOptions.add(150);
    }

    @FXML
    private void updateKlant() {
        try {
            this.klant = fxl.getKlant(textfield_NFCCode.getText());
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

    public void updateProducten() throws RemoteException {
        listview_ProductenVoorKlant.setItems(FXCollections.observableList(fxl.getProducten()));
        listview_ProductenVoorVoorraad.setItems(FXCollections.observableList(fxl.getProducten()));
    }

    public void ProductenKlantBestellingDoubleClicked() {
        System.out.println("Product list clicked");
        if (listview_ProductenVoorKlant.getSelectionModel().getSelectedItems().get(0) != null) {
            Product product = listview_ProductenVoorKlant.getSelectionModel().getSelectedItems().get(0);
            queuedKlantBestellingProducts.add(product);
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

    public void OpwaarderenClicked() throws RemoteException {
        if (this.klant == null) {
            System.out.println("No klant found.");
            new Alert(Alert.AlertType.INFORMATION, "Er is geen klant geselecteerd.", ButtonType.CLOSE).show();
        } else if (choiceBox_OpwaardeerMogelijkheden.getSelectionModel().getSelectedItem() != null) {
            int value = choiceBox_OpwaardeerMogelijkheden.getSelectionModel().getSelectedItem();
            fxl.SaldoVerhogen(klant, new Double(value));
            this.updateKlant();
        } else {
            System.out.println("No value was selected");
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
                sendBestellingToLogic(bestelling);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    public void ConfirmVoorraadOrderClicked() {
        System.out.println("Confirm order clicked");
        if (queuedVoorraadBestellingProducts.size() == 0) {
            System.out.println("No products found.");
            new Alert(Alert.AlertType.INFORMATION, "Er zijn geen producten geselecteerd om te verkopen.", ButtonType.CLOSE).show();
        } else {
            ArrayList<Product> producten = new ArrayList<>(this.queuedVoorraadBestellingProducts);

            try {
                System.out.println("Placing order...");
                sendBestellingToLogic(producten);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void emptyKlantQueuedOrder() {
        queuedKlantBestellingProducts.remove(0, queuedKlantBestellingProducts.size());
    }

    @FXML
    private void emptyVoorraadQueuedOrder() {
        queuedVoorraadBestellingProducts.remove(0, queuedVoorraadBestellingProducts.size());
    }

    private boolean sendBestellingToLogic(Bestelling bestelling) throws RemoteException {
        boolean success = fxl.placeKlantOrder(bestelling);
        emptyKlantQueuedOrder();
        updateKlant();
        updateProducten();
        return success;
    }

    private void sendBestellingToLogic(List<Product> producten) throws RemoteException {
        fxl.placeVoorraadOrder(producten);
        updateProducten();
        emptyVoorraadQueuedOrder();
    }
}
