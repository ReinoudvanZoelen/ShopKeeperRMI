package _shared.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Bestelling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public Date bestelMoment;
    public int BesteltijdInSeconden;

    @OneToMany(fetch = FetchType.EAGER)
    public List<Product> producten;

    @ManyToOne
    public Klant klant;
    public boolean completed;

    public Bestelling() {
        // Hibernate constructor
    }

    public Bestelling(int id, Date bestelMoment, int besteltijdInSeconden, List<Product> producten, Klant klant, boolean completed) {
        this.id = id;
        this.bestelMoment = bestelMoment;
        BesteltijdInSeconden = besteltijdInSeconden;
        this.producten = producten;
        this.klant = klant;
        this.completed = completed;
    }

    public Bestelling(Klant klant, List<Product> producten) {
        this.bestelMoment = new Date();
        BesteltijdInSeconden = (producten.size() * 3);
        this.producten = producten;
        this.klant = klant;
        this.completed = false;
    }

    @Override
    public String toString() {
        return "Bestelling{" +
                "id=" + id +
                ", bestelMoment=" + bestelMoment +
                ", BesteltijdInSeconden=" + BesteltijdInSeconden +
                ", producten=" + producten +
                ", klant=" + klant +
                ", completed=" + completed +
                '}';
    }
}
