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
        listeners = new ArrayList<>();

        // region Timer that publishes events (testing use)
        System.out.println("Timer running");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                index++;
                sendMessage("Message " + index);
            }
        }, 0, 500);
        // endregion
    }

    public void addListener(RemoteListener listener) {
        synchronized (lockListener) {
            listeners.add(listener);
            System.out.println("Listener added");
        }
    }

    public void removeListener(RemoteListener listener) {
        synchronized (lockListener) {
            listeners.remove(listener);
            System.out.println("Listener removed");
        }
    }

    public void sendMessage(String message) {
        synchronized (lockListener) {
            if (listeners.size() > 0) System.out.println("Publishing event to " + listeners.size() + " clients!");
            for (RemoteListener listener : listeners) {
                try {
                    listener.publish("Hello " + index);
                } catch (RemoteException ex) {
                    System.out.println("Client may not be available " + ex.toString());
                }
            }
        }
    }

}
