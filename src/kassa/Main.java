package kassa;

import javaFX.KassaFX.KassaFX;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Main {
    public static DataConnection_Klant DataConnection_Klant;
    public static DataConnection_Product DataConnection_Product;

    public static void main(String[] args) throws Exception {
        DataConnection_Klant = new DataConnection_Klant();
        DataConnection_Product = new DataConnection_Product();

        KassaFX.main(new String[0]);

        System.out.println("Press enter to exit.");
        System.in.read();
    }

    public static void StartProducten() throws Exception {
        throw new NotImplementedException();
    }

    public static void StartKlanten() throws Exception {
        throw new NotImplementedException();
    }
}

