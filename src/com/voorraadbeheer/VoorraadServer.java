package com.voorraadbeheer;

import com._shared.IVoorraadBeheer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class VoorraadServer {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("voorraadbeheer", new VoorraadBeheerImpl());
        System.out.println("Voorraadbeheerserver is up and running!");
    }
}
