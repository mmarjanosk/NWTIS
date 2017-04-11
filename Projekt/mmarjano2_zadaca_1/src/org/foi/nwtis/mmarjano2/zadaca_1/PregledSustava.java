/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;

/**
 *
 * @author Matija
 */
public class PregledSustava {

    Konfiguracija konf;
    Evidencija e;
    FileInputStream fileIn;
    ArrayList ar;

    public PregledSustava(String datoteka) throws IOException, ClassNotFoundException {

        FileInputStream fileIn = new FileInputStream(datoteka);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        e = (Evidencija) in.readObject();
        in.close();
        fileIn.close();
        pregledaj(e);

    }

    public void pregledaj(Evidencija e) {

        System.out.println("Broj prekinutih zahtjeva je " + e.getBrojPrekinutihZahtjeva());

        System.out.println("Broj uspješnih zahtjeva je " + e.getBrojUspjesnihZahtjeva());

        System.out.println("Broj zadnje dretve je " + e.getBrojZadnjeDretve());

        System.out.println("Broj ukupnih zahtjeva je " + e.getUkupnoZahtjeva());

        System.out.println("Ukupno trajanje dretvi je " + e.getUkupnoTrajanjeDretvi() + " milisekundi");

        System.out.println("Adresa zadnje dretve je " + e.getZadnjaAdresa());
        ar = e.getAdrese();
        System.out.println("----------------------------------");
        System.out.print("Adrese koje su dodali korisnici :> ");
        int count = 0;
        while (ar.size() > count) {
            System.out.print(ar.get(count) + " || ");
            count++;
        }
       

        System.out.println("");
        System.out.println("----------------------------------");
        System.out.println("Adrese i brojevi zahtjeva s pojedinih ");
        System.out.println("----------------------------------");

        count = 0;
        while (ar.size() > count) {
            int value = e.getZahtjeviZaAdrese(String.valueOf(ar.get(count)));
            System.out.println("||" + ar.get(count) + " || " + value + "||");
            count++;
        }

    }

}
