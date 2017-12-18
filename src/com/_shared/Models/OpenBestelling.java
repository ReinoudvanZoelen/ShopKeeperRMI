package com._shared.Models;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class OpenBestelling implements Serializable {
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

    @Override
    public String toString() {
        return "OpenBestelling{" +
                "bestelMoment=" + bestelMoment +
                ", BesteltijdInSeconden=" + BesteltijdInSeconden +
                ", product=" + product +
                ", aantal=" + aantal +
                '}';
    }
}
