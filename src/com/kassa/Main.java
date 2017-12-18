package com.kassa;


import com._shared.Interfaces.IKlantBeheer;
import com._shared.Interfaces.IVoorraadBeheer;

import java.rmi.Naming;

public class Main {
    public static IVoorraadBeheer voorraad;
    public static IKlantBeheer klantBeheer;

    public static void main(String[] args) throws Exception {
        voorraad = (IVoorraadBeheer) Naming.lookup("rmi://localhost:5100/voorraadbeheer");
        klantBeheer = (IKlantBeheer) Naming.lookup("rmi://localhost:5099/klantbeheer");

        System.out.println(voorraad.getProducten().get(0).toString());
        System.out.println(klantBeheer.getKlanten().get(0).toString());
    }
}