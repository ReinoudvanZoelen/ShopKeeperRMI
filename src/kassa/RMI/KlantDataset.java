package kassa.RMI;

import _shared.Interfaces.IKlantBeheer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class KlantDataset {

    private static IKlantBeheer klantBeheer = null; //TODO Remove static?

    public KlantDataset() {
        try {
            String rmi_registry = "rmi://localhost:5099/";

            Context namingContext = new InitialContext();

            String urlService = rmi_registry + "klantbeheer";
            System.out.println(namingContext.lookup(urlService).toString());

            klantBeheer = (IKlantBeheer) namingContext.lookup(urlService);
        } catch (NamingException e) {
            System.out.println("NamingContext could not be created.");
            e.printStackTrace();
        }
    }

    public IKlantBeheer getKlantBeheer() {
        return klantBeheer;
    }
}
