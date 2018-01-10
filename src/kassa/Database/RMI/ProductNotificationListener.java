package kassa.Database.RMI;

import _shared.Interfaces.RemoteListener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProductNotificationListener extends UnicastRemoteObject implements RemoteListener {

    public ProductNotificationListener() throws RemoteException {
        System.out.println("Remote listener constructed.");
    }

    @Override
    public void publish(String content) throws RemoteException {
        System.out.println("Content received: " + content);
    }
}
