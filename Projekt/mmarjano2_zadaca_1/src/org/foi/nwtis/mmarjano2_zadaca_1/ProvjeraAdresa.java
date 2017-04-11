/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2_zadaca_1;

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
    Evidencija evid = new Evidencija();
    boolean status = false;
    String adresa;
    RadnaDretva dretva;
    OutputStream os;

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
                        os.write("ERROR 10: Adresa ne postoji i nema slobodnog mjesta za njeno dodavanje".getBytes());
                        os.flush();
                        socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    if (evid.getState(adresa)) {
                        try {
                            os = socket.getOutputStream();
                            os.write("ERROR 11: Adresa već postoji".getBytes());
                            os.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            os = socket.getOutputStream();
                            os.write("OK".getBytes());
                            os.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    evid.setZahtjeviZaAdrese(adresa);
                    int a = evid.getZahtjeviZaAdrese(adresa);
                    System.err.println(a);
                    status = false;
                }
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void provjeriAdresu(String adresa, RadnaDretva dretva) {
        this.adresa = adresa;
        this.dretva = dretva;
        status = true;
    }

}
