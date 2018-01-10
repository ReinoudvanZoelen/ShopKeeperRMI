package javaFX.ProductenFX;

import _shared.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import kassa.Main;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controller {
    public ListView listview_Producten;
    public ListView listview_PendingBestellingen;
    public Button button_confirmOrder;
    public Button button_cancelOrder;
    public ListView listview_OpenBestellingen;

    private ObservableList<Product> queuedProducts = FXCollections.observableList(new ArrayList<Product>());


    @FXML
    protected void initialize() throws RemoteException {
        listview_Producten.setItems(FXCollections.observableList(Main.productBeheer.GetProducten()));
        listview_PendingBestellingen.setItems(queuedProducts);
    }

    public void ProductClicked() {
        System.out.println("Product list clicked");
        Product product;
        if (listview_Producten.getSelectionModel().getSelectedItems().get(0) instanceof Product) {
            product = (Product) listview_Producten.getSelectionModel().getSelectedItems().get(0);
            queuedProducts.add(product);
        }
    }

    public void ConfirmOrderClicked() {
        System.out.println("Confirm order clicked");
        if (queuedProducts.size() == 0) {
            System.out.println("No products found.");
            new Alert(Alert.AlertType.INFORMATION, "Er zijn geen producten geselecteerd om te verkopen.", ButtonType.CLOSE).show();
        } else {

        }
    }

    public void CancelOrderClicked() {
        System.out.println("Cancel order clicked");
        queuedProducts.remove(0, queuedProducts.size());
    }
}
