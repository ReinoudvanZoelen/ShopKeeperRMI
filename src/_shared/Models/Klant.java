package _shared.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Klant implements Serializable {

    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    public String nfccode;
    public String name;
    public double saldo;

    public Klant() {
        // Hibernate constructor
    }

    public Klant(String name, double saldo, String nfccode) {
        this.name = name;
        this.saldo = saldo;
        this.nfccode = nfccode;
    }


    @Override
    public String toString() {
        return "Klant{" +
                "nfccode='" + nfccode + '\'' +
                ", name='" + name + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
