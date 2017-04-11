/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;

/**
 *
 * @author grupa_2
 */
class RadnaDretva extends Thread {

    Socket socket;
    long milisStart;
    public static int brojZahtjeva;
    RedCekanja red = new RedCekanja();
    Konfiguracija konf;
    public static Evidencija e;
    ProvjeraAdresa pa;
    String adresa;
    short brojDretve = ServerSustava.ThreadNum;
    String imeDretve = "mmarjano2 - " + brojDretve;

    RadnaDretva(Konfiguracija konf, Socket socket, ProvjeraAdresa pa, Evidencija e) {
        this.socket = socket;
        this.konf = konf;
        this.pa = pa;
        this.setName(this.imeDretve);
        this.milisStart = System.currentTimeMillis();
        this.brojDretve++;
        ServerSustava.ThreadNum++;
        this.adresa = String.valueOf(socket.getInetAddress());
        this.e = e;
    }

    @Override
    public void interrupt() {

        try {
            red.take(this);
            AžurirajEvidenciju(false, this, konf, e);
            System.err.println("Dretve prekinuta");
            OutputStream os = socket.getOutputStream();
            os.write("ERROR13; Dretva prekinuta i nije zavrsila izvodenje".getBytes());
            os.flush();
            os.close();
        } catch (InterruptedException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }

        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        super.run(); //To change body of generated methods, choose Tools | Templates.
        System.out.println(this.getClass());

        String sintaksaPAUSE = "USER ([^\\s]+); PASSWD ([^\\s]+); PAUSE";
        String sintaksaSTART = "USER ([^\\s]+); PASSWD ([^\\s]+); START";
        String sintaksaSTOP = "USER ([^\\s]+); PASSWD ([^\\s]+); STOP";
        String sintaksaSTAT = "USER ([^\\s]+); PASSWD ([^\\s]+); STAT";
        String sintaksaWAIT = "USER ([^\\s]+); WAIT ([^\\s]+)$";
        String sintaksaADD = "USER ([^\\s]+); ADD ([^\\s]+)$";
        String sintaksaTEST = "USER ([^\\s]+); TEST ([^\\s]+)$";
        String provjera;
        AdminKomande ak = new AdminKomande();
        KlijentKomande kk = new KlijentKomande();

        InputStream is = null;
        OutputStream os = null;
        try {

            is = socket.getInputStream();
            os = socket.getOutputStream();

            StringBuffer sb = new StringBuffer();

            while (true) {
                int znak = is.read();
                if (znak == -1) {
                    break;
                }
                sb.append((char) znak);
            }

            System.out.println("Primljena naredba : " + sb);
            //TODO provjeriti ispravnos primljenog zahtjeva
            red.put(this);
            Pattern pattern = Pattern.compile(sintaksaPAUSE);
            Matcher m = pattern.matcher(sb);
            boolean status = m.matches();
            if (status) {

                provjera = m.group(1) + ";" + m.group(2);
                provjeraAdmina(provjera,konf);
                if (provjeraAdmina(provjera,konf) && ak.TestirajServer() == false) {

                    ak.ZaustaviServer();
                    System.out.println("Sustav Pauziran");
                    os.write("OK".getBytes());
                    AžurirajEvidenciju(status, this, konf, e);

                } else if (!provjeraAdmina(provjera,konf)) {
                    os.write("ERROR00; Neispravni autenfikacijski podaci".getBytes());
                    AžurirajEvidenciju(!status, this, konf, e);
                } else if (ak.TestirajServer() == true) {
                    os.write("ERROR01; System je vec u stanju pauze".getBytes());
                    AžurirajEvidenciju(!status, this, konf, e);
                }
                os.flush();
                os.close();
                red.take(this);
            }

            pattern = Pattern.compile(sintaksaSTART);
            m = pattern.matcher(sb);
            status = m.matches();
            if (status) {
                provjera = m.group(1) + ";" + m.group(2);
                provjeraAdmina(provjera,konf);
                if (provjeraAdmina(provjera,konf) && ak.TestirajServer() == true) {
                    ak.PokreniServer();
                    System.out.println("System ponovno pokrenut");
                    os.write("OK".getBytes());
                    AžurirajEvidenciju(status, this, konf, e);
                } else if (!provjeraAdmina(provjera,konf)) {
                    os.write("ERROR00; Neispravni autenfikacijski podaci".getBytes());
                    AžurirajEvidenciju(!status, this, konf, e);
                } else {
                    os.write("ERROR02; System nije u stanju pauze".getBytes());
                    AžurirajEvidenciju(!status, this, konf, e);
                }
                red.take(this);
                os.flush();
                os.close();
            }

            pattern = Pattern.compile(sintaksaSTOP);
            m = pattern.matcher(sb);
            status = m.matches();
            if (status) {
                provjera = m.group(1) + ";" + m.group(2);
                provjeraAdmina(provjera,konf);
                if (provjeraAdmina(provjera,konf)) {
                    ak.UgasiServer(konf, os, e, this);
                } else if (!provjeraAdmina(provjera,konf)) {
                    os.write("ERROR 00;Neispravni autenfikacijski podaci".getBytes());
                    AžurirajEvidenciju(!status, this, konf, e);

                } else {
                    os.write("ERROR 03; System nije ugašen uslijed pogreške".getBytes());
                    AžurirajEvidenciju(!status, this, konf, e);
                }
                red.take(this);
            }

            pattern = Pattern.compile(sintaksaSTAT);
            m = pattern.matcher(sb);
            status = m.matches();
            if (status) {
                provjera = m.group(1) + ";" + m.group(2);
                provjeraAdmina(provjera,konf);
                if (provjeraAdmina(provjera,konf)) {
                    System.out.println("Pokrenuta akcija STAT");
                    AžurirajEvidenciju(status, this, konf, e);
                    ak.Stat(konf, os, e);

                } else if (!provjeraAdmina(provjera,konf)) {
                    try {
                        os.write("ERROR00; Neispravni autenfikacijski podaci".getBytes());
                        os.flush();
                        os.close();
                        AžurirajEvidenciju(!status, this, konf, e);
                    } catch (IOException ex) {
                        Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    try {
                        os.write("ERROR03; Nešto nije u redu s evidencijskom datotekom".getBytes());
                        os.flush();
                        os.close();

                    } catch (IOException ex) {
                        Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                red.take(this);
            }

            if (!ak.TestirajServer()) {
                pattern = Pattern.compile(sintaksaWAIT);
                m = pattern.matcher(sb);
                status = m.matches();
                if (status) {
                    System.out.println("Pokrenuta akcija korisnika WAIT");

                    RadnaDretva dretva = this;
                    try {
                        kk.waitsec(dretva, m.group(2));
                        AžurirajEvidenciju(status, this, konf, e);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    red.take(this);
                }

                pattern = Pattern.compile(sintaksaADD);
                m = pattern.matcher(sb);
                status = m.matches();
                if (status) {
                    System.out.println("Pokrenuta akcija korisnika ADD");
                    kk.add(m.group(2), pa, this, e);
                }

                pattern = Pattern.compile(sintaksaTEST);
                m = pattern.matcher(sb);
                status = m.matches();
                if (status) {
                    System.out.println("Pokrenuta akcija korisnika TEST");
                    if (kk.testAdrese(m.group(2), e)) {
                        try {
                            os.write("OK;YES".getBytes());
                            os.flush();
                            os.close();
                            AžurirajEvidenciju(status, this, konf, e);
                        } catch (IOException ex) {
                            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (!kk.testAdrese(m.group(2), e)) {
                        try {
                            os.write("OK;NO".getBytes());
                            os.flush();
                            os.close();
                            AžurirajEvidenciju(status, this, konf, e);
                        } catch (IOException ex) {
                            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);

                        }

                    }
                }

                red.take(this);

            } else {
                red.take(this);
                System.err.println("Sustav je u stanju pause za sve osim Admina");
                this.interrupt();
            }

        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            try {
                os.write("ERROR90;Neispravna naredba".getBytes());
                os.flush();
                os.close();
            } catch (IOException ex1) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public long getMilisStart() {
        return milisStart;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean provjeraAdmina(String provjera,Konfiguracija konf) throws IOException {
        FileReader r;

        boolean test = false;
        try {
            String nazivdat = konf.dajPostavku("adminDatoteka");
            r = new FileReader(nazivdat);
            LineNumberReader lnr = new LineNumberReader(r);
            String line = null;
            while ((line = lnr.readLine()) != null) {

                if (line.equals(provjera)) {

                    test = true;

                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }

        return test;

    }

    public synchronized static void AžurirajEvidenciju(Boolean status, RadnaDretva dretva, Konfiguracija konf, Evidencija e) throws FileNotFoundException {
        long trajanjeDretvi = System.currentTimeMillis() - dretva.milisStart;

        if (status) {
            e.setBrojUspjesnihZahtjeva();
        } else {
            e.setBrojPrekinutihZahtjeva();
        }
        e.setBrojZadnjeDretve(dretva.brojDretve);
        e.setZahtjeviZaAdrese(String.valueOf(dretva.socket.getInetAddress()));
        e.setUkupnoTrajanjeDretvi(trajanjeDretvi);
        e.setZadnjaAdresa(String.valueOf(dretva.socket.getInetAddress()));
    }

}
