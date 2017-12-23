package javaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    public Button button_ConfirmOrder;
    public Button button_CancelOrder;
    public Button button_Klanten;
    public Button button_Producten;
    public TextField textfield_NFCCode;
    public ListView listview_TeBestellen;
    public ListView listview_OpenBestellingenKlant;
}
