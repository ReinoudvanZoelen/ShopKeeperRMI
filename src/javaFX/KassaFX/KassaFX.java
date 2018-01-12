package javaFX.KassaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KassaFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("javaFX/KassaFX/KassaFX.fxml"));
        primaryStage.setTitle("Kassa - Reinoud van Zoelen");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }
}
