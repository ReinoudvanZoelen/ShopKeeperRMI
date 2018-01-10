package _shared.Models;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;

public class Bestelling implements Serializable {
    public Date bestelMoment;
    public int BesteltijdInSeconden;
    public Product product;
    public int aantal;

    public Bestelling(int besteltijdInSeconden, Product product, int aantal) {
        this.bestelMoment = new Date();
        BesteltijdInSeconden = besteltijdInSeconden;
        this.product = product;
        this.aantal = aantal;
    }

    @Override
    public String toString() {
        return "Bestelling{" +
                "bestelMoment=" + bestelMoment +
                ", BesteltijdInSeconden=" + BesteltijdInSeconden +
                ", product=" + product +
                ", aantal=" + aantal +
                '}';
    }
}
