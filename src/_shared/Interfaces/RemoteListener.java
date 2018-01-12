/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _shared.Interfaces;

import java.util.*;
import java.rmi.*;

public interface RemoteListener extends EventListener, Remote{
    void publish(String content) throws RemoteException;
}
