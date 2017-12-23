package kassa;

public class Main {
    public static KlantClient KlantClient;

    public static void main(String[] args) throws Exception {
        KlantClient = new KlantClient();

        System.out.println(KlantClient.getKlanten().get(0).toString());

        System.out.println("Press enter to exit.");
        System.in.read();
    }
}