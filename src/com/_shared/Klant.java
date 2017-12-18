package com._shared;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Klant extends UnicastRemoteObject {
    int id;
    String name;
    double saldo;
    String nfccode;

    public Klant(int id, String name, double saldo, String nfccode) throws RemoteException {
        super();
        this.id = id;
        this.name = name;
        this.saldo = saldo;
        this.nfccode = nfccode;
    }


}
