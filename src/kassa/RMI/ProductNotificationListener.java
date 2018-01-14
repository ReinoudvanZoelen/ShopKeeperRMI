package kassa.RMI;

import _shared.Interfaces.RemoteListener;
import _shared.Interfaces.RemotePublisher;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProductNotificationListener extends UnicastRemoteObject implements RemoteListener {

    public ProductNotificationListener() throws RemoteException {
        try {
            String rmi_registry = "rmi://localhost:5100/";

            Context namingContext = new InitialContext();

            String urlService = rmi_registry + "notificationPublisher";

            System.out.println(namingContext.lookup(urlService).toString());

            // Get our RemotePublisher via RMI
            RemotePublisher publisher = (RemotePublisher) namingContext.lookup(urlService);

            // Add the listener to the Publisher
            publisher.addListener(this);

            System.out.println(namingContext.lookup(urlService).toString());

        } catch (NamingException e) {
            System.out.println("NamingContext could not be created.");
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void publish(String content) {
        System.out.println("Content received: " + content);
    }
}
