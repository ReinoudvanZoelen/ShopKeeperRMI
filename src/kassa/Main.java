package kassa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kassa.RMI.KlantDataset;
import kassa.RMI.ProductDataset;
import kassa.RMI.ProductNotificationListener;
import kassa.javaFX.KassaFXLogic;

public class Main extends Application {
    public static KassaFXLogic logic;

    public static void main(String[] args) {
        // https://stackoverflow.com/questions/25873769/launch-javafx-application-from-another-class
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Starting JavaFX");

        logic = new KassaFXLogic(new ProductDataset().getProductBeheer(),
                new KlantDataset().getKlantBeheer(),
                new ProductNotificationListener());

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("kassa/javaFX/KassaFX.fxml"));
        GridPane gridPane = loader.load();
        Scene scene = new Scene(gridPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}