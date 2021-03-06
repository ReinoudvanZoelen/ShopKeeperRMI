package kassa.RMI;

import _shared.Interfaces.IProductBeheer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProductDataset {

    private static IProductBeheer productBeheer = null; //TODO Remove static?

    public ProductDataset() {
        try {
            String rmi_registry = "rmi://localhost:5100/";

            Context namingContext = new InitialContext();

            String urlService = rmi_registry + "productbeheer";
            System.out.println(namingContext.lookup(urlService).toString());

            productBeheer = (IProductBeheer) namingContext.lookup(urlService);
        } catch (NamingException e) {
            System.out.println("NamingContext could not be created.");
            e.printStackTrace();
        }
    }

    public IProductBeheer getProductBeheer() {
        return productBeheer;
    }

}
