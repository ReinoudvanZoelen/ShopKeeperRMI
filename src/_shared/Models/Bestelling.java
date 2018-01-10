package _shared.Models;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;

public class Bestelling implements Serializable {
    public Date bestelMoment;
    public int BesteltijdInSeconden;
    public Product product;
    public Klant klant;
    public int aantal;
    public boolean completed;

    public Bestelling(int besteltijdInSeconden, Product product, Klant klant, int aantal) {
        this.bestelMoment = new Date();
        BesteltijdInSeconden = besteltijdInSeconden;
        this.klant = klant;
        this.product = product;
        this.aantal = aantal;
        this.completed = false;
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
