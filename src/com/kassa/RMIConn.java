package com.kassa;

public class RMIConn {
    private static final String baseUrl = "rmi://localhost:";
    private static final int KlantPort = 5099;
    private static final int VoorraadPort = 5100;

    public static String URLVoorraad(){return baseUrl + VoorraadPort + "/voorraadbeheer";}
    public static String URLKlant(){return baseUrl + KlantPort + "/klantbeheer";}
}
