package com.voorraadbeheer;

import com._shared.Interfaces.IVoorraadBeheer;
import com._shared.Models.OpenBestelling;
import com._shared.Models.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class VoorraadBeheerImpl extends UnicastRemoteObject implements IVoorraadBeheer {

    private ArrayList<Product> producten = new ArrayList<>();

    public VoorraadBeheerImpl() throws RemoteException {
        producten.add(new Product(0, "Cola", new Double(2.50)));
        producten.add(new Product(1, "Fanta", new Double(2.00)));
        producten.add(new Product(2, "Fristi", new Double(3.50)));
    }

    @Override
    public ArrayList<Product> getProducten() {
        return producten;
    }

    @Override
    public ArrayList<OpenBestelling> getOpenstaandeBestellingen() {
        throw new NotImplementedException();
    }

    @Override
    public int GetProductVoorraad(Product product) {
        int voorraad = 0;
        for (Product p : this.producten) {
            if (p.id == product.id) {
                voorraad++;
            }
        }
        return voorraad;
    }
}
