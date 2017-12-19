package com._shared.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIServer implements Remote {
    private ArrayList<RMIClient> clients = new ArrayList<>();

    public void Register(RMIClient client) throws RemoteException {
        System.out.println("Client is being registered");
        int startCount = clients.size();
        clients.add(client);
        if (startCount != clients.size()) {
            System.out.println("Client has successfully been added. Clients connected: " + clients.size());
            MessageAllClients("Ge meugt nie sloan opt oog");
        } else {
            System.out.println("Something went wrong, client was not successfully implemented");
        }
    }

    public void Unregister(RMIClient client) throws RemoteException {
        clients.remove(client);
    }

    public void MessageAllClients(String message) {
        for (RMIClient client : clients) {
            client.TransferMessage(message);
        }
    }
}
