package productbeheer;

import Database.HibernateBestellingRepository;
import Database.HibernateProductRepository;
import _shared.Interfaces.IProductBeheer;
import _shared.Models.Bestelling;
import _shared.Models.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProductBeheerImpl extends UnicastRemoteObject implements IProductBeheer {

    private HibernateProductRepository hiberProduct = new HibernateProductRepository();
    private HibernateBestellingRepository hiberBestelling = new HibernateBestellingRepository();

    public ProductBeheerImpl() throws RemoteException {
    }

    public List<Product> GetProducten() {
        return hiberProduct.findAll();
    }

    public void VerwerkBestelling(Bestelling bestelling) {
        // Save bestelling to database
        hiberBestelling.create(bestelling);

        // Lower the stock of every product by one
        for (Product p : bestelling.producten) {
            Product product = hiberProduct.findOne(p.id);
            product.voorraad--;
            hiberProduct.update(product);
        }
    }

    @Override
    public void VerwerkBestelling(List<Product> producten) {
        ProductServer.notificationPublisher.sendMessage("We gaan " + producten.size() + " producten bestellen!");

        // Raise the stock of every product by one
        for (final Product p : producten) {
            Product product = hiberProduct.findOne(p.id);
            product.voorraad++;
            hiberProduct.update(product);
        }

        ProductServer.notificationPublisher.sendMessage("De bestelde producten zijn op voorraad. Klik op verversen.");
    }
}
