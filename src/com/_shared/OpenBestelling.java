package com._shared;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class OpenBestelling extends UnicastRemoteObject {
    Date bestelMoment;
    int BesteltijdInSeconden;
    Product product;
    int aantal;

    public OpenBestelling(int besteltijdInSeconden, Product product, int aantal) throws RemoteException {
        super();
        this.bestelMoment = new Date();
        BesteltijdInSeconden = besteltijdInSeconden;
        this.product = product;
        this.aantal = aantal;
    }
}
