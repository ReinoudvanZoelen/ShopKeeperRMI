package kassa;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.IProductBeheer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kassa.Database.RMI.KlantDataset;
import kassa.Database.RMI.ProductDataset;
import kassa.Database.RMI.ProductNotificationListener;

public class Main extends Application {
    public static IProductBeheer productBeheer;
    public static IKlantBeheer klantBeheer;
    public static ProductNotificationListener productNotificationListener;

    public static void main(String[] args) throws Exception {
        productBeheer = new ProductDataset().getProductBeheer();
        klantBeheer = new KlantDataset().getKlantBeheer();
        productNotificationListener = new ProductNotificationListener();

        // https://stackoverflow.com/questions/25873769/launch-javafx-application-from-another-class
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Starting JavaFX");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("kassa/javaFX/KassaFX.fxml"));
        primaryStage.setTitle("ShopKeeper RMI - Reinoud van Zoelen");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }
}
