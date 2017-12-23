package javaFX;

import javaFX.KlantenFX.KlantenFX;
import javaFX.ProductenFX.ProductenFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("KassaFX.fxml"));
        primaryStage.setTitle("Kassa - Reinoud van Zoelen");
        primaryStage.setScene(new Scene(root, 400, 450));
        primaryStage.show();

        new KlantenFX().start(new Stage());
        new ProductenFX().start(new Stage());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
