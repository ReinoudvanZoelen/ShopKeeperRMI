package _shared.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePublisher extends Remote {
    void addListener(RemoteListener listener) throws RemoteException;

    void removeListener(RemoteListener listener) throws RemoteException;
}
