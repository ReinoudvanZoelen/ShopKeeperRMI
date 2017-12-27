package kassa;

public class RMIConn {
    private static final String baseUrl = "rmi://localhost:";
    private static final int KlantPort = 5099;
    private static final int ProductPort = 5100;

    public static String URLProduct(){return baseUrl + ProductPort + "/productbeheer";}
    public static String URLKlant(){return baseUrl + KlantPort + "/klantbeheer";}
}
