package klantbeheer;

import _shared.Interfaces.IKlantBeheer;
import _shared.Models.Bestelling;
import _shared.Models.Klant;
import _shared.Models.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class KlantBeheerImpl extends UnicastRemoteObject implements IKlantBeheer {

    private ArrayList<Klant> klanten = new ArrayList<>();

    protected KlantBeheerImpl() throws RemoteException {
        klanten.add(new Klant(0, "Reinoud", 50.00, "abc"));
        klanten.add(new Klant(1, "Niels", 15.00, "qwert"));
        klanten.add(new Klant(2, "Bas", 10.00, "werty"));
        klanten.add(new Klant(3, "Bono", 0.00, "1234"));
    }

    @Override
    public boolean SaldoVerhogen(Klant klant, Double hoeveelheid) {
        for (Klant k : klanten) {
            if (k.id == klant.id) {
                k.saldo += hoeveelheid;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean SaldoVerlagen(Klant klant, Double hoeveelheid) {
        for (Klant k : klanten) {
            if (k.id == klant.id) {
                k.saldo -= hoeveelheid;
                return true;
            }
        }
        return false;
    }

    @Override
    public Klant getKlant(int id) {
        for (Klant k : klanten) {
            if (k.id == id) {
                return k;
            }
        }
        System.out.println("No klant with id " + id + " was found!");
        return null;
    }

    @Override
    public Klant getKlantByNFC(String NFC) {
        for (Klant k : klanten) {
            if (k.nfccode.equals(NFC)) {
                return k;
            }
        }
        System.out.println("No klant with NFC code " + NFC + " was found!");
        return null;
    }

    @Override
    public ArrayList<Klant> getKlanten() {
        return this.klanten;
    }

    @Override
    public void BetaalBestelling(Bestelling bestelling) {
        this.SaldoVerlagen(bestelling.klant, calculateTotalPrice(bestelling.producten));
    }

    private double calculateTotalPrice(ArrayList<Product> producten) {
        double totalPrice = 0;

        for (Product p : producten) {
            totalPrice += p.prijs;
        }

        return totalPrice;
    }
}
