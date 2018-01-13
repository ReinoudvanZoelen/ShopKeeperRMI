package _shared.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public String naam;
    public double prijs;
    public int voorraad;

    public Product() {
        // Hibernate constructor
    }

    public Product(String naam, double prijs, int voorraad) {
        this.naam = naam;
        this.prijs = prijs;
        this.voorraad = voorraad;
    }

    @Override
    public String toString() {
        BigDecimal bd = new BigDecimal(this.prijs).setScale(2, RoundingMode.HALF_UP);
        return naam + ": €" + bd + ", Voorraad: " + voorraad;
    }
}
