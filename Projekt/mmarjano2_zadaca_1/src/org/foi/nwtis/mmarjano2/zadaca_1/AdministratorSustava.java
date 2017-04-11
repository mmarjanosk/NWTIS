/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Matija
 * Klasa zadužena za pokretanje Admin korisnika
 */
public class AdministratorSustava extends KorisnikSustava {

    public AdministratorSustava() {

    }
    /**
     * Metoda koja provjerava sintaksu admin zahtjeva te formira komandu za serverr u skladu s istom
     * @param poruka - zahtjev iz kojeg se formira komanda za server
     */
    public void spojiSeNaServer(String poruka) {
        String sintaksaAdmin = "^-admin -server ([^\\s]+) -port ([0-9]{4}) -u ([a-zA-Z0-9\\\\._\\\\-]{0,}) -p ([a-zA-Z0-9\\\\_\\\\-\\\\#\\\\!]{0,}) -(pause|start|stop|stat)$";
        Pattern pattern = Pattern.compile(sintaksaAdmin);
        Matcher m = pattern.matcher(poruka);
        boolean status = m.matches();
        if (status) {
            String server = m.group(1);
            int port = Integer.parseInt(m.group(2));
            String zahtjev = "USER " + m.group(3) + "; PASSWD " + m.group(4) + "; " + m.group(5).toUpperCase();
            super.pokreniKorisnika(server, port, zahtjev);
        }
    }

}

//zahtjev = "USER pero; PASSWD 123456; PAUSE";

