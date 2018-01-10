package _shared.Models;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public class Bestelling implements Serializable {
    public Date bestelMoment;
    public int BesteltijdInSeconden;
    public ArrayList<Product> producten;
    public Klant klant;
    public int aantal;
    public boolean completed;

    public Bestelling(Klant klant, ArrayList<Product> producten) {
        this.bestelMoment = new Date();
        BesteltijdInSeconden = (producten.size() * 3);
        this.producten = producten;
        this.klant = klant;
        this.completed = false;
    }


    @Override
    public String toString() {
        return "Bestelling{" +
                "bestelMoment=" + bestelMoment +
                ", BesteltijdInSeconden=" + BesteltijdInSeconden +
                ", klant=" + klant +
                ", aantal=" + aantal +
                ", completed=" + completed +
                '}';
    }
}
