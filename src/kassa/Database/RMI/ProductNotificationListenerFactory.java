package kassa.Database.RMI;

import _shared.Interfaces.RemotePublisher;
import kassa.RMITools;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProductNotificationListenerFactory {

    private ProductNotificationListener pnl;

    public ProductNotificationListenerFactory() {
        try {
            try {
                String rmi_registry = "rmi://localhost:5100/";

                Context namingContext = new InitialContext();

                RMITools.PrintPublishedServices(rmi_registry);


                String urlService = rmi_registry + "notificationPublisher";


                System.out.println(namingContext.lookup(urlService).toString());

                // Get our RemotePublisher via RMI
                RemotePublisher publisher = (RemotePublisher) namingContext.lookup(urlService);

                // Create a new Listener (this client)
                ProductNotificationListener aListener = new ProductNotificationListener();

                // Add the listener to the Publisher
                publisher.addListener(aListener);

                System.out.println(namingContext.lookup(urlService).toString());

                this.pnl = aListener;
            } catch (NamingException e) {
                System.out.println("NamingContext could not be created.");
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ProductNotificationListener getListener() {
        return pnl;
    }

    public ProductNotificationListener getProductNotificationListener() {
        return pnl;
    }
}


