package _shared.Interfaces;

import _shared.Models.Bestelling;
import _shared.Models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IProductBeheer extends Remote {
    List<Product> GetProducten() throws RemoteException;

    void VerwerkBestelling(Bestelling bestelling) throws RemoteException;

    void VerwerkBestelling(List<Product> producten) throws RemoteException;
}
