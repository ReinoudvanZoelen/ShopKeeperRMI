package klantbeheer;

import Database.HibernateKlantRepository;
import _shared.Interfaces.IKlantBeheer;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class KlantBeheerImpl extends UnicastRemoteObject implements IKlantBeheer {

    private HibernateKlantRepository hiberKlant = new HibernateKlantRepository();

    protected KlantBeheerImpl() throws RemoteException {
    }

    public boolean SaldoVerhogen(Klant klant, Double hoeveelheid) {
        double startsaldo = getKlant(klant.nfccode).saldo;

        klant.saldo = round((startsaldo + hoeveelheid), 2);
        hiberKlant.update(klant);

        return startsaldo > klant.saldo;
    }


    public boolean SaldoVerlagen(Klant klant, Double hoeveelheid) {
        double startsaldo = getKlant(klant.nfccode).saldo;

        System.out.println("Startsaldo: " + startsaldo);
        System.out.println("Hoeveelheid: " + hoeveelheid);

        double newSaldo = startsaldo - hoeveelheid;
        double newSaldoRoundeed = round(newSaldo, 2);

        System.out.println("Nieuw saldo: " + newSaldo);
        klant.saldo = newSaldoRoundeed;
        hiberKlant.update(klant);

        System.out.println("New saldo on server: " + getKlant(klant.nfccode).saldo);

        return startsaldo < getKlant(klant.nfccode).saldo;
    }


    public Klant getKlant(String NFC) {
        for (Klant k : hiberKlant.findAll()) {
            if (k.nfccode.equals(NFC)) {
                return k;
            }
        }
        System.out.println("No klant with NFC code " + NFC + " was found!");
        return null;
    }


    public List<Klant> getKlanten() {
        return hiberKlant.findAll();
    }


    public void BetaalBestelling(Bestelling bestelling) {
        // Lower saldo of the klant by the amount of the bestelling
        System.out.println("@@@@@@@@@@@@@@@@@@@@@ Bestelling betalen voltooid: " + this.SaldoVerlagen(bestelling.klant, calculateTotalPrice(bestelling.producten)));
    }

    private double calculateTotalPrice(List<Product> producten) {
        double totalPrice = 0;

        for (Product p : producten) {
            totalPrice += p.prijs;
        }

        return totalPrice;
    }
}
