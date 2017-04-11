/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.mmarjano2.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author grupa_2
 */
public class KorisnikSustava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NemaKonfiguracije {

        //TODO ne zaboravi ostale moguće parametre DONE                                                        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }

        String p = sb.toString().trim();
        argMatcher(p);

    }

    protected static void pokreniKorisnika(String nazivServera, int port, String zahtjev) {
        InputStream is = null;
        OutputStream os = null;
        Socket socket = null;

        System.out.println(nazivServera);
        System.out.println(port);
        System.out.println(zahtjev);

        try {
            socket = new Socket(nazivServera, port);

            is = socket.getInputStream();
            os = socket.getOutputStream();
            os.write(zahtjev.getBytes());
            os.flush();
            socket.shutdownOutput();

            StringBuffer sb = new StringBuffer();

            while (true) {
                int znak = is.read();
                if (znak == -1) {
                    break;
                }
                sb.append((char) znak);
            }

            System.out.println("Primljena naredba (SAMO ZA KONTROLU) : <] " + sb + " [>");
            try {

                String E90 = "ERROR90; ([a-zA-Z0-9_\\s]+)$";
                provjera(E90, sb);
                String E00 = "ERROR00; ([a-zA-Z0-9_\\s]+)$";
                provjera(E00, sb);
                String E01 = "ERROR01; ([a-zA-Z0-9_\\s]+)$";
                provjera(E01, sb);
                String E02 = "ERROR02; ([a-zA-Z0-9_\\s]+)$";
                provjera(E02, sb);
                String E03 = "ERROR03;([a-zA-Z0-9_\\s]+)$";
                provjera(E03, sb);
                String E04 = "ERROR04; ([a-zA-Z0-9_\\s]+)$";
                provjera(E04, sb);
                String E11 = "ERROR11; ([a-zA-Z0-9_\\s]+)$";
                provjera(E11, sb);
                String E12 = "ERROR12; ([a-zA-Z0-9_\\s]+)$";
                provjera(E12, sb);
                String E13 = "ERROR13; ([a-zA-Z0-9_\\s]+)$";
                provjera(E13, sb);
                String OK = "OK; LENGTH ([a-zA-Z0-9_\\s]+)$";
                provjera(OK, sb);

            } catch (Exception e) {
            }

            //TODO provjeriti ispravnos primljenog zahtjeva
        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void argMatcher(String poruka) throws NemaKonfiguracije {

        String sintaksaAdmin = "^-admin -server ([^\\s]+) -port ([0-9]{4}) -u ([a-zA-Z0-9\\\\._\\\\-]{0,}) -p ([a-zA-Z0-9\\\\_\\\\-\\\\#\\\\!]{0,}) -(pause|start|stop|stat)$";
        String sintaksaKlijent = "^-user -s ([^\\s]+) -port ([0-9]{4}) -u ([a-zA-Z0-9\\\\._\\\\-]{0,}) -([a|t]\\s.{0,}|[w]\\s.{1,3})$";
        String sintaksaPregled = "^-prikaz -s ([^\\s]+)$";
        String user = "";

        //matcher za Admina
        Pattern pattern = Pattern.compile(sintaksaAdmin);
        Matcher m = pattern.matcher(poruka);
        boolean status = m.matches();
        if (status) {
            user = "admin";
            int poc = 0;
            int kraj = m.groupCount();
            for (int i = poc; i <= kraj; i++) {
                System.out.println(i + ". " + m.group(i));
            }
        }

        //matcher za Klijenta
        pattern = Pattern.compile(sintaksaKlijent);
        m = pattern.matcher(poruka);
        status = m.matches();
        if (status) {
            user = "klijent";
            int poc = 0;
            int kraj = m.groupCount();
            for (int i = poc; i <= kraj; i++) {
                System.out.println(i + ". " + m.group(i));
            }
        }

        //matcher za Pregled
        pattern = Pattern.compile(sintaksaPregled);
        m = pattern.matcher(poruka);
        status = m.matches();
        if (status) {
            user = "pregled";
            int poc = 0;
            int kraj = m.groupCount();
            for (int i = poc; i <= kraj; i++) {
                System.out.println(i + ". " + m.group(i));
            }

        }

        if (user == "admin") {
            System.out.println("Pokrecem Admina");
            System.out.println();
            AdministratorSustava admin = new AdministratorSustava();
            admin.spojiSeNaServer(poruka);

        } else if (user == "klijent") {
            System.out.println("Pokrecem Klijenta");
            KlijentSustava kl = new KlijentSustava();
            kl.spojiSeNaServer(poruka);
        } else if (user == "pregled") {
            try {
                System.out.println("Pokrecem pregled sustava");
                PregledSustava ps = new PregledSustava(m.group(1));

            } catch (IOException ex) {
                Logger.getLogger(KorisnikSustava.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KorisnikSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Ne odgovara!");
        }

        //KorisnikSustava.pokreniKorisnika(nazivServera, port);
    }

    public static void provjera(String sintaksa, StringBuffer poruka) {
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(poruka);
        boolean status = m.matches();
        if (status) {
            System.out.println(m.group(1));
        }
        else {
      
    }
    }
}
