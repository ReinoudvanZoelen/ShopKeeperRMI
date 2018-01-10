package _shared.Models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;

public class Product implements Serializable {
    public int id;
    public String naam;
    public double prijs;

    public Product(int id, String naam, double prijs) {
        super();
        this.id = id;
        this.naam = naam;
        this.prijs = prijs;
    }

    @Override
    public String toString() {
        BigDecimal bd = new BigDecimal(this.prijs).setScale(2, RoundingMode.HALF_UP);
        return naam + ": €" + bd;
    }
}
