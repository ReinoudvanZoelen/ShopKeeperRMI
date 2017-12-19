package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClient extends Remote {
    void TransferMessage(String message) throws RemoteException;
}
