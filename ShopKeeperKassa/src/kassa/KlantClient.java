package kassa;

import Interfaces.IKlantBeheer;
import Interfaces.RMIClient;
import Models.Klant;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class KlantClient implements RMIClient, Serializable {
    private IKlantBeheer klantbeheer;

    public KlantClient() {
        try {
            klantbeheer = (IKlantBeheer) Naming.lookup(RMIConn.URLKlant());
            klantbeheer.Register(this);

            ArrayList<Klant> klanten = klantbeheer.getKlanten();
            klantbeheer.SaldoVerhogen(klanten.get(0), new Double(100.00));

            klantbeheer.MessageAllClients("Eentje op uw oooooooooooog");
        } catch (Exception ex) {
            System.out.println("Could not find Klantbeheer");
            ex.printStackTrace();
        }
    }

    public ArrayList<Klant> getKlanten() throws RemoteException {
        return klantbeheer.getKlanten();
    }

    @Override
    public void TransferMessage(String message) throws RemoteException {
        System.out.println("Message received from the server: " + message);
    }
}
