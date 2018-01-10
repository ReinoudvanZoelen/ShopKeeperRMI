package productbeheer;

import _shared.Interfaces.IProductBeheer;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ProductBeheerImpl extends UnicastRemoteObject implements IProductBeheer {

    private ArrayList<Product> producten = new ArrayList<>();
    private ArrayList<Bestelling> bestellingen = new ArrayList<>();

    public ProductBeheerImpl() throws RemoteException {
        producten.add(new Product(0, "Cola", 2.50));
        producten.add(new Product(1, "Fanta", 2.00));
        producten.add(new Product(2, "Fristi", 3.50));
        producten.add(new Product(3, "7-Up", 2.20));
    }

    @Override
    public ArrayList<Product> GetProducten() throws RemoteException {
        return producten;
    }

    @Override
    public ArrayList<Bestelling> GetOpenstaandeBestellingen() {
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

    @Override
    public boolean RemoveItemFromStockOnce(Product product) {
        for (int i = 0; i < producten.size(); i++) {
            if (producten.get(i).id == product.id) {
                producten.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void VerwerkBetestelling(Bestelling bestelling) {
        for (Product product : producten) {
            // TODO: Implement lowering stock by 1
            //RemoveItemFromStockOnce(product);
        }

        bestellingen.add(bestelling);

        //TODO: Start a thread that processes the order after X seconds

    }
}
