package com.klantbeheer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class KlantbeheerServer {

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("klantbeheer", new KlantBeheerImpl());
        System.out.println("Klantbeheerserver is up and running!");
    }
}
