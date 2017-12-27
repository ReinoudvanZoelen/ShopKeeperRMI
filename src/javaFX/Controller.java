package javaFX;

import _shared.Models.Klant;
import _shared.Models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import kassa.Main;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controller {

    private Klant klant = null;
    private ArrayList<Product> queuedOrder = new ArrayList<>();

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
        listview_TeBestellen.getItems().addAll(Main.ProductClient.getProducten());
    }

    public void NFCChanged() {
        System.out.println("NFC Changed. Content: " + textfield_NFCCode.getText());

        try {
            this.klant = Main.KlantClient.getKlantByNFC(textfield_NFCCode.getText());
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
    }

    public void ConfirmOrderClicked() {
        System.out.println("Confirm order clicked");
    }

    public void CancelOrderClicked() {
        System.out.println("Cancel order clicked");
    }

    public void KlantenClicked() {
        System.out.println("Klanten clicked");
    }

    public void ProductenClicked() {
        System.out.println("Producten clicked");
    }
}
