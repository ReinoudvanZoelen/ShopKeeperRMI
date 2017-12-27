package kassa;

import javaFX.KassaFX;
import javafx.stage.Stage;

public class Main {
    public static KlantClient KlantClient;
    public static ProductClient ProductClient;

    public static void main(String[] args) throws Exception {
        KlantClient = new KlantClient();
        ProductClient = new ProductClient();

        KassaFX.main(null);

        System.out.println("Press enter to exit.");
        System.in.read();
    }
}