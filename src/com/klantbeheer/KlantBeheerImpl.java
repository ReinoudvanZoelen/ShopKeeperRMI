package com.klantbeheer;

import com._shared.Interfaces.IKlantBeheer;
import com._shared.Models.Klant;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class KlantBeheerImpl extends UnicastRemoteObject implements IKlantBeheer {
    private ArrayList<Klant> klanten = new ArrayList<>();

    protected KlantBeheerImpl() throws RemoteException {
        klanten.add(new Klant(0, "Reinoud", new Double(100.00), "897y326t872345h"));
        klanten.add(new Klant(1, "Reinoud", new Double(80.00), "645ersw45wyyw45"));
        klanten.add(new Klant(2, "Reinoud", new Double(10.00), "yw454w5y45e6y45y"));
        klanten.add(new Klant(3, "Reinoud", new Double(0.00), "wy454wyhwse4hy4w5"));
    }

    @Override
    public boolean SaldoVerhogen(Klant klant, Double hoeveelheid) throws RemoteException {
        for (Klant k : klanten) {
            if (k.id == klant.id) {
                k.saldo += hoeveelheid;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean SaldoVerlagen(Klant klant, Double hoeveelheid) throws RemoteException {
        for (Klant k : klanten) {
            if (k.id == klant.id) {
                k.saldo -= hoeveelheid;
                return true;
            }
        }
        return false;
    }

    @Override
    public Klant getKlant(int id) throws RemoteException {
        for (Klant k : klanten) {
            if (k.id == id) {
                return k;
            }
        }
        System.out.println("No klant with id " + id + " was found!");
        return null;
    }

    @Override
    public Klant getKlantByNFC(String NFC) throws RemoteException {
        for (Klant k : klanten) {
            if (k.nfccode == NFC) {
                return k;
            }
        }
        System.out.println("No klant with NFC code " + NFC + " was found!");
        return null;
    }

    @Override
    public double getSaldo(Klant klant) throws RemoteException {
        for (Klant k : klanten) {
            if (k.id == klant.id) {
                return k.saldo;
            }
        }
        System.out.println("No klant with ID " + klant.id + " was found, saldo could not be returned.");
        return 0;
    }

    @Override
    public ArrayList<Klant> getKlanten() throws RemoteException {
        return this.klanten;
    }
}
