package com.kassa;

import com._shared.IVoorraadBeheer;
import java.rmi.Naming;

public class Main {
    public static IVoorraadBeheer voorraad;

    public static void main(String[] args) throws Exception {
        voorraad = (IVoorraadBeheer) Naming.lookup("rmi://localhost:5099/voorraadbeheer");

        System.out.println(voorraad.getFirstProduct());

        //System.out.println(voorraad.getFirstProduct());
    }
}
