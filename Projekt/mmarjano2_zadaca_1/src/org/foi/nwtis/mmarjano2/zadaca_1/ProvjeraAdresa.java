/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;

/**
 *
 * @author grupa_2
 */
class ProvjeraAdresa extends Thread {

    Konfiguracija konf;
    boolean status = false;
    String adresa;
    RadnaDretva dretva;
    OutputStream os;
    Evidencija evid;

    ProvjeraAdresa(Konfiguracija konf) {
        this.konf = konf;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        int trajanjeSpavanja = Integer.parseInt(konf.dajPostavku("intervalAdresneDretve"));

        while (true) {

            try {
                sleep(trajanjeSpavanja);
            } catch (InterruptedException ex) {
                Logger.getLogger(RezervnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (status) {

                Socket socket = dretva.getSocket();

                if (evid.getSize() == 10) {

                    try {
                        os = socket.getOutputStream();
                        os.write("ERROR10; Adresa ne postoji i nema slobodnog mjesta za njeno dodavanje".getBytes());
                        os.flush();
                        os.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    if (evid.getState(adresa)) {
                        try {
                            os = socket.getOutputStream();
                            os.write("ERROR11; Adresa vec postoji".getBytes());
                            os.flush();
                            os.close();
                        } catch (IOException ex) {
                            Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            os = socket.getOutputStream();
                            os.write("OK".getBytes());
                            os.flush();
                            os.close();
                        } catch (IOException ex) {
                            Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    evid.setAdrese(adresa);
                    evid.setZahtjeviZaAdrese(adresa);
                    
                    status = false;
                }
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void provjeriAdresu(String adresa, RadnaDretva dretva,Evidencija evid) {
        this.adresa = adresa;
        this.evid=evid;
        this.dretva = dretva;
        status = true;
    }

}
