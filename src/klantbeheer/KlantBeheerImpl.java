package klantbeheer;

import _shared.Interfaces.IKlantBeheer;
import _shared.Interfaces.RMIClient;
import _shared.Models.Klant;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class KlantBeheerImpl extends UnicastRemoteObject implements IKlantBeheer {

    private ArrayList<RMIClient> clients = new ArrayList<>();

    private ArrayList<Klant> klanten = new ArrayList<>();

    protected KlantBeheerImpl() throws RemoteException {
        klanten.add(new Klant(0, "Reinoud", new Double(100.00), "abc"));
        klanten.add(new Klant(1, "Niels", new Double(80.00), "qwert"));
        klanten.add(new Klant(2, "Bas", new Double(10.00), "werty"));
        klanten.add(new Klant(3, "Bono", new Double(0.00), "1234"));
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
    public double getSaldo(Klant klant) {
        for (Klant k : klanten) {
            if (k.id == klant.id) {
                return k.saldo;
            }
        }
        System.out.println("No klant with ID " + klant.id + " was found, saldo could not be returned.");
        return 0;
    }

    @Override
    public ArrayList<Klant> getKlanten() {
        return this.klanten;
    }

    @Override
    public void Register(RMIClient client) {
        System.out.println("Adding a new client.");
        clients.add(client);
    }

    @Override
    public void Unregister(RMIClient client) {
        System.out.println("Removing a client.");
        clients.remove(client);
    }

    @Override
    public void MessageAllClients(String message) throws RemoteException {
        System.out.println("Sending a message to " + clients.size() + " clients.");
        for (RMIClient client : clients) {
            client.TransferMessage("Message from KlantServer: " + message);
        }
    }
}
