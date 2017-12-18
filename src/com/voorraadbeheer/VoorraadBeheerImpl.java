package com.voorraadbeheer;

import com._shared.IVoorraadBeheer;
import com._shared.OpenBestelling;
import com._shared.Product;
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
        return 0;
    }

    @Override
    public Product getFirstProduct() throws RemoteException {
        return producten.get(0);
    }

    @Override
    public String talktome() throws RemoteException {
        return "Hallo";
    }
}
