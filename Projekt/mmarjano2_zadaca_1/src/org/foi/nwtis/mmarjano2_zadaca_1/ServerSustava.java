/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2_zadaca_1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mmarjano2.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author grupa_2
 */
public class ServerSustava {
    public static boolean pause=false;
    public static short ThreadNum=0;
    Evidencija e=new Evidencija();

    public static void main(String[] args) throws IOException, InterruptedException {

        String sintaksa1 = "^-konf ([^\\s]+\\.(?i))(txt|xml|bin)( +-load)?$";
        
        //TODO napraviti
        boolean trebaUcitatiEvidenciju = false;
        
        
       
       
       

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }

        String p = sb.toString().trim();
        Pattern pattern = Pattern.compile(sintaksa1);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        if (status) {
            int poc = 0;
            int kraj = m.groupCount();
            for (int i = poc; i <= kraj; i++) {
                System.out.println(i + ". " + m.group(i));
            }

            String nazivDatoteke = m.group(1) + m.group(2);

            if (m.group(3) != null) {
                trebaUcitatiEvidenciju = true;
            }

            ServerSustava server = new ServerSustava();
            server.pokreniServer(nazivDatoteke, trebaUcitatiEvidenciju);

        } else {
            System.out.println("Ne odgovara!");
        }
    }

    private void pokreniServer(String nazivDatoteke, boolean trebaUcitatiEvidenciju) throws IOException, InterruptedException {
         
        try {
            Konfiguracija konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
           

            int port = Integer.parseInt(konf.dajPostavku("port"));
            int kapacitet = Integer.parseInt(konf.dajPostavku("maksBrojRadnihDretvi"));
            

            NadzorDretvi nd = new NadzorDretvi(konf);
            nd.start();
            ProvjeraAdresa pa = new ProvjeraAdresa(konf);
            pa.start();
            RezervnaDretva rd = new RezervnaDretva(konf);
            rd.start();
           

            ServerSocket ss = new ServerSocket(port);

            while (true) {
                Socket socket = ss.accept();
             
               
                RadnaDretva dretva = new RadnaDretva(konf,socket,pa,e);
               
               
                if(RedCekanja.red.size()==kapacitet){
                    rd.op(dretva);
                    
                    
                } else {
                    int num = RedCekanja.red.size();
                    num++;
                    
                    dretva.start();
                    System.err.println("Započinjanje dretve "+num+"/"+kapacitet);
                }
                //TODO dodaj dretvu u kolekciju aktivnih radnih dretvi
                
                //TODO treba provjeriti ima li  mjesta za radnu dretvu
            }

        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
