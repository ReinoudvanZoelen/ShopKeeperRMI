package kassa;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.IProductBeheer;
import javaFX.KassaFX.KassaFX;
import kassa.Database.RMI.DBKlant;
import kassa.Database.RMI.DBProduct;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.RemoteException;

public class Main {
    public static IProductBeheer productBeheer;
    public static IKlantBeheer klantBeheer;

    static {
        try {
            productBeheer = new DBProduct().getProductBeheer();
            klantBeheer = new DBKlant().getKlantBeheer();
        } catch (RemoteException e) { e.printStackTrace(); }
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

