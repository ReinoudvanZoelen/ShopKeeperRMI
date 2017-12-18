package com._shared.Interfaces;

import com._shared.Models.Klant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IKlantBeheer extends Remote {
    boolean SaldoVerhogen(Klant klant, Double hoeveelheid) throws RemoteException;

    boolean SaldoVerlagen(Klant klant, Double hoeveelheid) throws RemoteException;

    Klant getKlant(int id) throws RemoteException;

    Klant getKlantByNFC(String NFC) throws RemoteException;

    double getSaldo(Klant klant) throws RemoteException;

    ArrayList<Klant> getKlanten() throws RemoteException;

}
