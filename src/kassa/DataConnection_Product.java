package kassa;

import _shared.Interfaces.IProductBeheer;
import _shared.Interfaces.RMIClient;
import _shared.Models.Product;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DataConnection_Product implements RMIClient, Serializable{
    private IProductBeheer productbeheer;

    public DataConnection_Product() {
        try {
            productbeheer = (IProductBeheer) Naming.lookup(RMIConn.URLProduct());
            productbeheer.Register(this);

            productbeheer.MessageAllClients("Test message to all connected clients.");
        } catch (Exception ex) {
            System.out.println("Could not find Klantbeheer");
            ex.printStackTrace();
        }
    }

    public ArrayList<Product> getProducten() throws RemoteException{
        return productbeheer.getProducten();
    }

    @Override
    public void TransferMessage(String message) throws RemoteException {
        System.out.println("Message received from the server: " + message);
    }
}
