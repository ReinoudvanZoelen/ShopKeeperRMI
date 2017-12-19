package Models;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;

public class OpenBestelling implements Serializable {
    public Date bestelMoment;
    public int BesteltijdInSeconden;
    public Product product;
    public int aantal;

    public OpenBestelling(int besteltijdInSeconden, Product product, int aantal) throws RemoteException {
        super();
        this.bestelMoment = new Date();
        BesteltijdInSeconden = besteltijdInSeconden;
        this.product = product;
        this.aantal = aantal;
    }

    @Override
    public String toString() {
        return "OpenBestelling{" +
                "bestelMoment=" + bestelMoment +
                ", BesteltijdInSeconden=" + BesteltijdInSeconden +
                ", product=" + product +
                ", aantal=" + aantal +
                '}';
    }
}
