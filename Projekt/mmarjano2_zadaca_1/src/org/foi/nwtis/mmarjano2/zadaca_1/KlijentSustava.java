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
 */
public class KlijentSustava extends KorisnikSustava {

    public KlijentSustava() {

    }

    public void spojiSeNaServer(String poruka) {
       
        String sintaksaAdd = "^-user -s ([^\\s]+) -port ([0-9]{4}) -u ([a-zA-Z0-9\\\\._\\\\-]{0,}) -a ([^\\s]+)$";
        String sintaksaTest = "^-user -s ([^\\s]+) -port ([0-9]{4}) -u ([a-zA-Z0-9\\\\._\\\\-]{0,}) -t ([^\\s]+)$";
        String sintaksaWait = "^-user -s ([^\\s]+) -port ([0-9]{4}) -u ([a-zA-Z0-9\\\\._\\\\-]{0,}) -w ([0-9]{0,3})$";
        String zahtjev;
        String server;
        int port;
        
        
        //matcher za ADD
        Pattern pattern = Pattern.compile(sintaksaAdd);
        Matcher m = pattern.matcher(poruka);
        boolean status = m.matches();
        if(status){
            server=m.group(1);
            port=Integer.valueOf(m.group(2));
            zahtjev="USER "+m.group(3)+"; ADD "+m.group(4);
            super.pokreniKorisnika(server,port,zahtjev);
        }
        
        pattern = Pattern.compile(sintaksaTest);
        m = pattern.matcher(poruka);
        status = m.matches();
        if(status){
            server=m.group(1);
            port=Integer.valueOf(m.group(2));
            zahtjev="USER "+m.group(3)+"; TEST "+m.group(4);
            System.out.println(zahtjev);
            super.pokreniKorisnika(server,port,zahtjev);
        }
        
        pattern = Pattern.compile(sintaksaWait);
        m = pattern.matcher(poruka);
        status = m.matches();
        if(status){
            server=m.group(1);
            port=Integer.valueOf(m.group(2));
            zahtjev="USER "+m.group(3)+"; WAIT "+m.group(4);
            System.out.println(zahtjev);
            super.pokreniKorisnika(server,port,zahtjev);
        }
           
           
            //String zahtjev = "USER " +m.group(3)+"; PASSWD "+m.group(4)+"; "+m.group(5).toUpperCase();
            //super.pokreniKorisnika(server,port,zahtjev);
        }
    }


