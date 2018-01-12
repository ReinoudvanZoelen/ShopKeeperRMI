package javaFX.KlantenFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KlantenFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("KlantenFX.fxml"));
        primaryStage.setTitle("Klantbeheer");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }
}
