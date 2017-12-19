package voorraadbeheer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class VoorraadServer {

    protected VoorraadServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5100);
        registry.rebind("voorraadbeheer", new VoorraadBeheerImpl());
        System.out.println("Voorraadbeheerserver is up and running!");
    }
}
