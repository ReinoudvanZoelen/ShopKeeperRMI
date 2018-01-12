package klantbeheer;

import _shared.Interfaces.IKlantBeheer;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class KlantBeheerImpl extends UnicastRemoteObject implements IKlantBeheer {

    private List<Klant> klanten = new ArrayList<>();

    protected KlantBeheerImpl() throws RemoteException {
    }

    public boolean SaldoVerhogen(Klant klant, Double hoeveelheid) {
        for (Klant k : klanten) {
            if (k.nfccode.equals(klant.nfccode)) {
                k.saldo += hoeveelheid;
                return true;
            }
        }
        return false;
    }


    public boolean SaldoVerlagen(Klant klant, Double hoeveelheid) {
        for (Klant k : klanten) {
            if (k.nfccode.equals(klant.nfccode)) {

                k.saldo -= hoeveelheid;
                return true;
            }
        }
        return false;
    }


    public Klant getKlant(String NFC) {
        for (Klant k : klanten) {
            if (k.nfccode.equals(NFC)) {
                return k;
            }
        }
        System.out.println("No klant with NFC code " + NFC + " was found!");
        return null;
    }


    public List<Klant> getKlanten() {
        return this.klanten;
    }


    public void BetaalBestelling(Bestelling bestelling) {
        this.SaldoVerlagen(bestelling.klant, calculateTotalPrice(bestelling.producten));
    }

    private double calculateTotalPrice(List<Product> producten) {
        double totalPrice = 0;

        for (Product p : producten) {
            totalPrice += p.prijs;
        }

        return totalPrice;
    }
}
