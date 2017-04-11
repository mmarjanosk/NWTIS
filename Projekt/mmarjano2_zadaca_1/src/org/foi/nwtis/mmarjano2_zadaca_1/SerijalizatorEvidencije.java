/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2_zadaca_1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;

/**
 *
 * @author grupa_2
 */
class SerijalizatorEvidencije extends Thread {

    Konfiguracija konf;
    Evidencija e;
    boolean status = false;
    ObjectOutputStream out=null;
    FileOutputStream fileOut=null;
    
    

    SerijalizatorEvidencije(Konfiguracija konf) throws FileNotFoundException {
        this.konf = konf;
        fileOut = new FileOutputStream(konf.dajPostavku("evidDatoteka"));
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        
        while(true) {

            
           

            if (status) {

                
                try {
                    out = new ObjectOutputStream(fileOut);
                    
                } catch (IOException ex) {
                    Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                   
                    out.writeObject(e);
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
                }
              
                try {
                    fileOut.close();
                } catch (IOException ex) {
                    Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.printf("Serijalizirani podaci su spremljeni u "+konf.dajPostavku("evidDatoteka"));
                status = false;
            }   
        }

    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void serialize(Evidencija e) {
        this.e = e;
        this.status = true;
        
    }

}
