package _shared.Interfaces;

import _shared.Models.OpenBestelling;
import _shared.Models.Product;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IProductBeheer extends RMIServer {
    ArrayList<Product> getProducten() throws RemoteException;

    ArrayList<OpenBestelling> getOpenstaandeBestellingen() throws RemoteException;

    int GetProductVoorraad(Product product) throws RemoteException;

    boolean removeItemOnce(Product product) throws RemoteException;
}
