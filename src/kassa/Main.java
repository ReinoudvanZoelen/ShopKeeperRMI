package kassa;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.IProductBeheer;
import javaFX.KassaFX.KassaFX;
import kassa.Database.RMI.KlantDataset;
import kassa.Database.RMI.ProductDataset;
import kassa.Database.RMI.ProductNotificationListener;
import kassa.Database.RMI.ProductNotificationListenerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Main {
    public static IProductBeheer productBeheer;
    public static IKlantBeheer klantBeheer;
    public static ProductNotificationListener productNotificationListener;

    static {
        productBeheer = new ProductDataset().getProductBeheer();
        klantBeheer = new KlantDataset().getKlantBeheer();
        productNotificationListener = new ProductNotificationListenerFactory().getListener();
    }

    public static void main(String[] args) throws Exception {
        KassaFX.main(new String[0]);

        System.out.println("Press enter to exit.");
        System.in.read();
    }

    public static void StartProducten() {
        throw new NotImplementedException();
    }

    public static void StartKlanten() {
        throw new NotImplementedException();
    }
}

