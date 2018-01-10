package kassa.Database.RMI;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.IProductBeheer;
import kassa.RMITools;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DBKlant {

    private IKlantBeheer klantBeheer;

    public DBKlant() {
        try {
            String rmi_registry = "rmi://localhost:5099/";

            Context namingContext = new InitialContext();

            RMITools.PrintPublishedServices(rmi_registry);

            String urlService = rmi_registry + "klantbeheer";
            System.out.println(namingContext.lookup(urlService).toString());

            this.klantBeheer = (IKlantBeheer) namingContext.lookup(urlService);
        } catch (NamingException e) {
            System.out.println("NamingContext could not be created.");
            e.printStackTrace();
        }
    }

    public IKlantBeheer getKlantBeheer() {
        return this.klantBeheer;
    }
}
