/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _shared.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;

public interface RemoteListener extends EventListener, Remote{
    void publish(String content) throws RemoteException;
}
