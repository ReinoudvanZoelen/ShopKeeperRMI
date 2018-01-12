package _shared.Interfaces;

import java.rmi.*;

public interface RemotePublisher extends Remote {
    void addListener(RemoteListener listener) throws RemoteException;

    void removeListener(RemoteListener listener) throws RemoteException;
}
