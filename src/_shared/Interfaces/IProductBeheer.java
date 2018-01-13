package _shared.Interfaces;

import _shared.Models.Bestelling;
import _shared.Models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IProductBeheer extends Remote {
    List<Product> GetProducten() throws RemoteException;

    List<Bestelling> GetOpenstaandeBestellingen() throws RemoteException;

    int GetProductVoorraad(Product product) throws RemoteException;

    void VerwerkBetestelling(Bestelling bestelling) throws RemoteException;
}
