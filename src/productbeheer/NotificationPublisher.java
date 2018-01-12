package productbeheer;

import _shared.Interfaces.RemoteListener;
import _shared.Interfaces.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationPublisher extends UnicastRemoteObject implements RemotePublisher {
    private static int index = 0;
    private ArrayList<RemoteListener> listeners;
    private Object lockListener = new Object();

    public NotificationPublisher() throws RemoteException {
        listeners = new ArrayList<RemoteListener>();

        // region Timer that publishes events (testing use)
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer running");
                synchronized (lockListener) {
                    index++;
                    for (RemoteListener listener : listeners) {
                        try {
                            listener.publish("Hello " + index);
                            System.out.println("Publishing event!");
                        } catch (RemoteException ex) {
                            System.out.println("Client may not be available " + ex.toString());
                        }
                    }
                }
            }
        }, 0, 500);
        // endregion
    }

    public void addListener(RemoteListener listener) throws RemoteException {
        synchronized (lockListener) {
            listeners.add(listener);
            System.out.println("Listener added");
        }
    }

    public void removeListener(RemoteListener listener) throws RemoteException {
        synchronized (lockListener) {
            listeners.remove(listener);
            System.out.println("Listener removed");
        }
    }
}
