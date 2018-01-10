/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _shared.Interfaces;

import java.beans.*;
import java.util.*;
import java.rmi.*;

/**
 *
 * @author Long Nguyen
 */
public interface RemoteListener extends EventListener, Remote{
    public void publish(String content) throws RemoteException;
}
