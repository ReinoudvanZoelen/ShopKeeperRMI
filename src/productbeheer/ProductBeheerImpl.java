package productbeheer;

import _shared.Interfaces.IProductBeheer;
import _shared.Models.Bestelling;
import _shared.Models.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ProductBeheerImpl extends UnicastRemoteObject implements IProductBeheer {

    private ArrayList<Product> producten = new ArrayList<Product>();
    private ArrayList<Bestelling> bestellingen = new ArrayList<Bestelling>();

    public ProductBeheerImpl() throws RemoteException {
        producten.add(new Product(0, "Cola", 2.50));
        producten.add(new Product(1, "Fanta", 2.00));
        producten.add(new Product(2, "Fristi", 3.50));
        producten.add(new Product(3, "7-Up", 2.20));
    }

    public ArrayList<Product> GetProducten() throws RemoteException {
        return producten;
    }

    public ArrayList<Bestelling> GetOpenstaandeBestellingen() throws RemoteException {
        throw new NotImplementedException();
    }

    public int GetProductVoorraad(Product product) throws RemoteException {
        int voorraad = 0;
        for (Product p : this.producten) {
            if (p.id == product.id) {
                voorraad++;
            }
        }
        return voorraad;
    }

    public void VerwerkBetestelling(Bestelling bestelling) throws RemoteException {
        for (Product product : producten) {
            // TODO: Implement lowering stock by 1
            //RemoveItemFromStockOnce(product);
        }

        bestellingen.add(bestelling);

        //TODO: Start a thread that processes the order after X seconds

    }
}
