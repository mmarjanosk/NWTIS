/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import static org.foi.nwtis.mmarjano2.zadaca_1.RadnaDretva.e;

/**
 *
 * @author Matija
 * Pomoćna klasa koja barata s komandama i u skladu s njima izvodi traženu akciju
 */
public class AdminKomande {
    
    
    public static boolean pause;
    
    public AdminKomande() {
        
    }
    
    /**
     * Metoda koja služi za promjenu stanja servera u stanje PAUSE po konceptu prekidača
     */
    public synchronized void ZaustaviServer(){
        pause=true;                
    }
    
    
    /**
     * Metoda koja služi za promjenu stanja servera u stanje RESUME po konceptu prekidača
     */
     public synchronized void PokreniServer(){
       pause=false;                
    }
    
     /**
      * Metoda koja služi za vraćanje stanja servera
      * @return pause - true(server je pauziran) ili false(server nije pauziran)
      */
     public boolean TestirajServer(){
        return pause;                
    }
     
    /**
     * Metoda za gašenje servera
     * @param konf -trenutna konfiguracija
     * @param os   -output stream dretve
     * @param e    -aktualni objekt evidencije
     * @param dretva - dretva koja se obrađuje
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InterruptedException 
     */ 
    public synchronized void UgasiServer(Konfiguracija konf,OutputStream os,Evidencija e,RadnaDretva dretva) throws FileNotFoundException, IOException, InterruptedException{
        System.out.println("Pokrenuta akcija STOP");
                        SerijalizatorEvidencije se = new SerijalizatorEvidencije(konf);
                        se.start();
                        se.serialize(e);
                       
                        se.interrupt();
                        os.write("OK".getBytes());
                        os.flush();
                        os.close();
                        dretva.sleep(1000);
        System.exit(0);
    }
    /**
     * Metoda koja šalje odgovor s trenutnom veličinom evidencijske datoteke
     * @param konf -trenutna konfiguracija
     * @param os   -output stream dretve 
     * @param e    -aktualni objekt evidencije
     * @throws IOException 
     */
    public void Stat(Konfiguracija konf,OutputStream os,Evidencija e) throws IOException{
        try {
            SerijalizatorEvidencije se= new SerijalizatorEvidencije(konf);
            se.start();
            se.serialize(e);
            se.interrupt();
            FileInputStream  fileinputstream =new FileInputStream(konf.dajPostavku("evidDatoteka"));
            long du=fileinputstream.getChannel().size();
            os.write(("OK; LENGTH "+du).getBytes());
            os.flush();
            os.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminKomande.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}