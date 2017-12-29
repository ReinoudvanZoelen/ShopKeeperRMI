package productbeheer;

import _shared.Interfaces.IProductBeheer;
import _shared.Interfaces.RMIClient;
import _shared.Models.OpenBestelling;
import _shared.Models.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ProductBeheerImpl extends UnicastRemoteObject implements IProductBeheer {

    private ArrayList<RMIClient> clients = new ArrayList<>();

    private ArrayList<Product> producten = new ArrayList<>();

    public ProductBeheerImpl() throws RemoteException {
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

    @Override
    public boolean removeItemOnce(Product product) {
        int startcount = producten.size();
        for (int i = 0; i < producten.size(); i++) {
            if (producten.get(i).id == product.id) {
                producten.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void Register(RMIClient client) {
        System.out.println("Adding a new client.");
        clients.add(client);
    }

    @Override
    public void Unregister(RMIClient client) {
        System.out.println("Removing a client.");
        clients.remove(client);
    }

    @Override
    public void MessageAllClients(String message) throws RemoteException {
        System.out.println("Sending a message to " + clients.size() + " clients.");
        for (RMIClient client : clients) {
            client.TransferMessage("Message from ProductServer: " + message);
        }
    }
}
