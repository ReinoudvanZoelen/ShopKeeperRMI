package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer extends Remote {
    void Register(RMIClient client) throws RemoteException;

    void Unregister(RMIClient client) throws RemoteException;

    void MessageAllClients(String message) throws RemoteException;
}
