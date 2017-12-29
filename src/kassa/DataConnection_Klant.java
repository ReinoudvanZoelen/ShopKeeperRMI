package kassa;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.RMIClient;
import _shared.Models.Klant;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DataConnection_Klant implements RMIClient, Serializable {
    private IKlantBeheer klantbeheer;

    public DataConnection_Klant() {
        try {
            klantbeheer = (IKlantBeheer) Naming.lookup(RMIConn.URLKlant());
            klantbeheer.Register(this);

            klantbeheer.MessageAllClients("Test message to all connected clients.");
        } catch (Exception ex) {
            System.out.println("Could not find Klantbeheer");
            ex.printStackTrace();
        }
    }

    public ArrayList<Klant> getKlanten() throws RemoteException {
        return klantbeheer.getKlanten();
    }

    public Klant getKlant(int id) throws RemoteException {
        return klantbeheer.getKlant(id);
    }

    public Klant getKlantByNFC(String NFC) throws RemoteException {
        return klantbeheer.getKlantByNFC(NFC);
    }

    public boolean SaldoVerhogen(Klant klant, Double hoeveelheid) throws RemoteException {
        return klantbeheer.SaldoVerhogen(klant, hoeveelheid);
    }

    public boolean SaldoVerlagen(Klant klant, Double hoeveelheid) throws RemoteException {
        return klantbeheer.SaldoVerlagen(klant, hoeveelheid);
    }

    @Override
    public void TransferMessage(String message) {
        System.out.println("Message received from the server: " + message);
    }
}
