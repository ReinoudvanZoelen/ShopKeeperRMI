package Models;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Klant implements Serializable {
    public int id;
    public String name;
    public double saldo;
    public String nfccode;

    public Klant(int id, String name, double saldo, String nfccode) throws RemoteException {
        super();
        this.id = id;
        this.name = name;
        this.saldo = saldo;
        this.nfccode = nfccode;
    }

    @Override
    public String toString() {
        return "Klant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", saldo=" + saldo +
                ", nfccode='" + nfccode + '\'' +
                '}';
    }
}
