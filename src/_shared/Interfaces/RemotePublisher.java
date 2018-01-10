package _shared.Interfaces;

import java.rmi.*;

public interface RemotePublisher extends Remote {
    public void addListener(RemoteListener listener) throws RemoteException;

    public void removeListener(RemoteListener listener) throws RemoteException;
}
