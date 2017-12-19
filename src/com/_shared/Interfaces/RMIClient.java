package com._shared.Interfaces;

import java.rmi.Remote;

public interface RMIClient extends Remote {
    void TransferMessage(String message);
}
