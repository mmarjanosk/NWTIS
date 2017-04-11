/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2_zadaca_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;

/**
 *
 * @author Matija
 */
public class AdminKomande {
    
    
    public static boolean pause;
    
    public AdminKomande() {
        
    }
    
    public synchronized void ZaustaviServer(){
        pause=true;                
    }
    
     public synchronized void PokreniServer(){
       pause=false;                
    }
     
     public boolean TestirajServer(){
        return pause;                
    }
     
    public synchronized void UgasiServer(Konfiguracija konf,OutputStream os,Evidencija e) throws FileNotFoundException, IOException{
        SerijalizatorEvidencije se= new SerijalizatorEvidencije(konf);
        se.start();
        se.serialize(e);
        se.interrupt();
        os.write("OK".getBytes());
        os.flush();
        os.close();
        System.exit(1);
    }
    
    public void Stat(Konfiguracija konf,OutputStream os,Evidencija e) throws FileNotFoundException, IOException{
        SerijalizatorEvidencije se= new SerijalizatorEvidencije(konf);
        se.start();
        se.serialize(e);
        se.interrupt();
        FileInputStream  fileinputstream =new FileInputStream(konf.dajPostavku("evidDatoteka"));
        long du=fileinputstream.getChannel().size();
        os.write(("OK; LENGTH "+du).getBytes());
        os.flush();
        os.close();
    }
    
}