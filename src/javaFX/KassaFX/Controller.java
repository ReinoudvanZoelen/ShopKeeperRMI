package javaFX.KassaFX;

import _shared.Models.Klant;
import _shared.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kassa.Main;

import java.lang.reflect.Array;
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
        listview_TeBestellen.setItems(FXCollections.observableList(Main.DataConnection_Product.getProducten()));
        listview_OpenBestellingenKlant.setItems(queuedProducts);
    }

    public void NFCChanged() {
        System.out.println("NFC Changed. Content: " + textfield_NFCCode.getText());

        try {
            this.klant = Main.DataConnection_Klant.getKlantByNFC(textfield_NFCCode.getText());
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
            ArrayList<Product> producten = new ArrayList<>();
            for (Product p : this.queuedProducts) {
                producten.add(p);
            }
            if (this.klant == null) {
                new Alert(Alert.AlertType.INFORMATION, "Er is geen klant geselecteerd.", ButtonType.CLOSE).show();
            } else {
                Betalen(this.klant, producten);
            }
        }
    }

    public void CancelOrderClicked() {
        System.out.println("Cancel order clicked");
        queuedProducts.remove(0, queuedProducts.size());
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

    private boolean Betalen(Klant klant, ArrayList<Product> producten) {
        System.out.println("Betalen, pannekoek!");

        boolean hasPaid = false;
        boolean inStock = false;

        // Controleren of er wel genoeg saldo is


        // Voorraad controleren


        // Bedrag afschrijven


        // Voorraad bijwerken


        // Resultaat retourneren

        return true;
    }
}
