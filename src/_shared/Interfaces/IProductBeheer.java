package _shared.Interfaces;

import _shared.Models.Bestelling;
import _shared.Models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IProductBeheer extends Remote {
    ArrayList<Product> GetProducten() throws RemoteException;

    ArrayList<Bestelling> GetOpenstaandeBestellingen() throws RemoteException;

    int GetProductVoorraad(Product product) throws RemoteException;

    boolean RemoveItemFromStockOnce(Product product) throws RemoteException;
}
