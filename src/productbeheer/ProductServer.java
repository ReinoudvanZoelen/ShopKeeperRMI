package productbeheer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ProductServer {

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5100);
        registry.rebind("productbeheer", new ProductBeheerImpl());
        System.out.println("Productserver is up and running!");
    }
}
