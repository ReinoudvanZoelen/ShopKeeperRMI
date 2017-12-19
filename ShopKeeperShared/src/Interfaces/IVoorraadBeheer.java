package Interfaces;

import Models.OpenBestelling;
import Models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IVoorraadBeheer extends Remote {
    ArrayList<Product> getProducten() throws RemoteException;

    ArrayList<OpenBestelling> getOpenstaandeBestellingen() throws RemoteException;

    int GetProductVoorraad(Product product) throws RemoteException;
}
