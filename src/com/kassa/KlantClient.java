package com.kassa;

import com._shared.Interfaces.IKlantBeheer;
import com._shared.Interfaces.RMIClient;
import com._shared.Interfaces.RMIServer;
import com._shared.Models.Klant;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class KlantClient implements RMIClient {
    private IKlantBeheer klantbeheer;

    public KlantClient() {
        try {
            klantbeheer = (IKlantBeheer) Naming.lookup(RMIConn.URLKlant());
            klantbeheer.server.Register(this);
        } catch (Exception ex) {
            System.out.println("Could not find Klantbeheer");
            ex.printStackTrace();
        }
    }

    public ArrayList<Klant> getKlanten() throws RemoteException {
        return klantbeheer.getKlanten();
    }

    @Override
    public void TransferMessage(String message) {
        System.out.println("Message received from the server: " + message);
    }
}
