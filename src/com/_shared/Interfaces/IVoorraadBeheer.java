package com._shared.Interfaces;

import com._shared.Models.OpenBestelling;
import com._shared.Models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IVoorraadBeheer extends Remote {

    ArrayList<Product> getProducten() throws RemoteException;

    ArrayList<OpenBestelling> getOpenstaandeBestellingen() throws RemoteException;

    int GetProductVoorraad(Product product) throws RemoteException;
}
