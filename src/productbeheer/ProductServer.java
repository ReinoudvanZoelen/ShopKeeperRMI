package productbeheer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ProductServer {

    public static NotificationPublisher notificationPublisher;

    static {
        try {
            notificationPublisher = new NotificationPublisher();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5100);
        registry.rebind("productbeheer", new ProductBeheerImpl());
        System.out.println("Productserver is up and running!");

        registry.rebind("notificationPublisher", ProductServer.notificationPublisher);
        System.out.println("Notification Publisher is up and running!");
    }
}
