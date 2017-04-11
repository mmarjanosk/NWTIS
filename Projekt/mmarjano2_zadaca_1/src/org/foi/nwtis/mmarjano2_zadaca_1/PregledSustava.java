/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2_zadaca_1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mmarjano2.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author Matija
 */
public class PregledSustava {

    Konfiguracija konf;
    Evidencija e;
    FileInputStream fileIn;

    public PregledSustava(String datoteka) throws IOException, ClassNotFoundException {
       
        
        FileInputStream fileIn = new FileInputStream(datoteka);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        e = (Evidencija) in.readObject();
        in.close();
        fileIn.close();
        pregledaj(e);
        
    }

    public void pregledaj (Evidencija e) {
        
        System.out.println("Broj prekinutih zahtjeva je " + e.getBrojPrekinutihZahtjeva());
        System.out.println("Broj uspješnih zahtjeva je " + e.getBrojUspjesnihZahtjeva());
        System.out.println("Broj zadnje dretve je " + e.getBrojZadnjeDretve());
        System.out.println("Broj ukupnih zahtjeva je " + e.getUkupnoZahtjeva());
        System.out.println("Broj ukupnih zahtjeva je " + e.getUkupnoTrajanjeDretvi());
        System.out.println("Adresa zadnje dretve je " + e.getZadnjaAdresa());
    
    }
    
    
}
