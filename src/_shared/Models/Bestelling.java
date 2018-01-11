package _shared.Models;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public class Bestelling implements Serializable {
    public int id;
    public Date bestelMoment;
    public int BesteltijdInSeconden;
    public ArrayList<Product> producten;
    public Klant klant;
    public boolean completed;

    public Bestelling(int id, Date bestelMoment, int besteltijdInSeconden, ArrayList<Product> producten, Klant klant, boolean completed) {
        this.id = id;
        this.bestelMoment = bestelMoment;
        BesteltijdInSeconden = besteltijdInSeconden;
        this.producten = producten;
        this.klant = klant;
        this.completed = completed;
    }

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
                ", completed=" + completed +
                '}';
    }
}
