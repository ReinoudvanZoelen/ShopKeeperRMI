package _shared.Interfaces;

import _shared.Models.Bestelling;
import _shared.Models.Klant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IKlantBeheer extends Remote {
    void SaldoVerhogen(Klant klant, Double hoeveelheid) throws RemoteException;

    void SaldoVerlagen(Klant klant, Double hoeveelheid) throws RemoteException;

    Klant getKlant(String NFC) throws RemoteException;

    List<Klant> getKlanten() throws RemoteException;

    void BetaalBestelling(Bestelling bestelling) throws RemoteException;
}
