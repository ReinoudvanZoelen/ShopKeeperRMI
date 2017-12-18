package com._shared.Models;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Product implements Serializable {
    public int id;
    public String naam;
    public double prijs;

    public Product(int id, String naam, double prijs) throws RemoteException {
        super();
        this.id = id;
        this.naam = naam;
        this.prijs = prijs;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", prijs=" + prijs +
                '}';
    }
}