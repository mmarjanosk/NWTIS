/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2_zadaca_1;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;

/**
 *
 * @author grupa_2
 */
class NadzorDretvi extends Thread {

    Konfiguracija konf;
    long trenutnoVrijeme;
    long vrijemeZavršetka;


    long vrijemePočetkaD;
    
    
    NadzorDretvi(Konfiguracija konf) {
        this.konf = konf;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        
       
        while (true) {
            
            try {
                String vrijemeSpavanjStr=konf.dajPostavku("intervalNadzorneDretve");
                long vrijemeSpavanja=Long.parseLong(vrijemeSpavanjStr);
                
                sleep(vrijemeSpavanja);
                String tim = konf.dajPostavku("maksVrijemeRadneDretve");
                long vrijemeTrajanja = Long.parseLong(tim);
                
                 if (!RedCekanja.red.isEmpty()) {
                
                for (RadnaDretva dretva : RedCekanja.red) {
                    trenutnoVrijeme = System.currentTimeMillis();
                    vrijemePočetkaD = dretva.getMilisStart();
                    vrijemeZavršetka = vrijemePočetkaD + vrijemeTrajanja;
                    if (trenutnoVrijeme > vrijemeZavršetka) {
                        System.err.println("Dretva " + dretva.getName() + " prešla granicu");
                        dretva.interrupt();
                    }

                }
            }
            } catch (Exception e) {
            }

            //TODO dovršiti
            //TODO provjeriti trajanje pojedine aktivne radne dretve iz kolekcije
            
            //TODO razmisliti kako izaći iz infinite loopa
        }
        }
    

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
