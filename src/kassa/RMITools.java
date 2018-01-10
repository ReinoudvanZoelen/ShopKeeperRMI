package kassa;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import java.lang.management.ManagementFactory;
import java.util.Enumeration;

public class RMITools {

    public static void PrintPublishedServices(String RMIUrl) {
        try {
            System.out.println("VM machine [" + ManagementFactory.getRuntimeMXBean().getName() + "]");
            System.out.print("RMI registry bindings: \n");

            Enumeration<NameClassPair> e = new InitialContext().list(RMIUrl);
            while (e.hasMoreElements()) {
                System.out.println("Service: " + e.nextElement().getName());
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
