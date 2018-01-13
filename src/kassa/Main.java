package kassa;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.IProductBeheer;
import javafx.stage.Stage;
import kassa.Database.RMI.KlantDataset;
import kassa.Database.RMI.ProductDataset;
import kassa.Database.RMI.ProductNotificationListener;
import kassa.javaFX.KassaFX.KassaFX;
import kassa.javaFX.ProductenFX.ProductenFX;

public class Main {
    public static IProductBeheer productBeheer;
    public static IKlantBeheer klantBeheer;
    public static ProductNotificationListener productNotificationListener;

    public static void main(String[] args) throws Exception {
        productBeheer = new ProductDataset().getProductBeheer();
        klantBeheer = new KlantDataset().getKlantBeheer();
        productNotificationListener = new ProductNotificationListener();

        KassaFX.main(new String[0]);

        System.out.println("Press enter to exit.");
        System.in.read();
    }

    public static void StartProducten() throws Exception {
        new ProductenFX().start(new Stage());
    }

    public static void StartKlanten() {
//        new KlantenFX.main(new String[0]);
    }
}

