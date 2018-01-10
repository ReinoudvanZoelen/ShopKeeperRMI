package kassa.Database.RMI;

import _shared.Interfaces.IProductBeheer;
import kassa.RMITools;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProductDataset extends UnicastRemoteObject {

    private static IProductBeheer productBeheer;

    public ProductDataset() throws RemoteException {
        try {
            String rmi_registry = "rmi://localhost:5100/";

            Context namingContext = new InitialContext();

            RMITools.PrintPublishedServices(rmi_registry);

            String urlService = rmi_registry + "productbeheer";
            System.out.println(namingContext.lookup(urlService).toString());

            this.productBeheer = (IProductBeheer) namingContext.lookup(urlService);
        } catch (NamingException e) {
            System.out.println("NamingContext could not be created.");
            e.printStackTrace();
        }
    }

    public IProductBeheer getProductBeheer() {
        return this.productBeheer;
    }

}
