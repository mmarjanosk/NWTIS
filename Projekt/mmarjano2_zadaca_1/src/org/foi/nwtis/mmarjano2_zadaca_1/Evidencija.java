/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2_zadaca_1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author grupa_2
 */
public class Evidencija implements Serializable {
   
    private int brojUspjesnihZahtjeva=0;
    private int brojPrekinutihZahtjeva=0;
    private  Map<String,Integer> zahtjeviZaAdrese= new HashMap<>();
    private int brojZadnjeDretve=0;
    private long ukupnoTrajanjeDretvi=0;
    private String ZadnjaAdresa="";
    private int ukupnoZahtjeva=0;

    
    
    
    
    
    public int getUkupnoZahtjeva() {
        ukupnoZahtjeva = brojUspjesnihZahtjeva+brojPrekinutihZahtjeva;
        return ukupnoZahtjeva;
    }

    public String getZadnjaAdresa() {
        return ZadnjaAdresa;
    }

    public void setZadnjaAdresa(String ZadnjaAdresa) {
        this.ZadnjaAdresa = ZadnjaAdresa;
    }
    
   

    public int getSize() {
        return zahtjeviZaAdrese.size();
    }
    
    public boolean getState (String adresa){
        return zahtjeviZaAdrese.containsKey(adresa);
    }
    
    public boolean contains (String adresa){
        return zahtjeviZaAdrese.containsKey(adresa);
    }

   

    public int getBrojUspjesnihZahtjeva() {
        return brojUspjesnihZahtjeva;
    }

    public synchronized void setBrojUspjesnihZahtjeva() {
        this.brojUspjesnihZahtjeva++;
    }

    public int getBrojPrekinutihZahtjeva() {
        return brojPrekinutihZahtjeva;
    }

    public synchronized void setBrojPrekinutihZahtjeva() {
        this.brojPrekinutihZahtjeva++;
    }

    public int getZahtjeviZaAdrese(String adresa) {
        return zahtjeviZaAdrese.get(adresa);
    }

    public synchronized void setZahtjeviZaAdrese(String adresa) {
        if(zahtjeviZaAdrese.containsKey(adresa)){
            int value= zahtjeviZaAdrese.get(adresa)+1;
            zahtjeviZaAdrese.put(adresa, value);
            System.out.println("Adresa updateana");
        } else {
            zahtjeviZaAdrese.put(adresa, 0);
            System.out.println("Adresa dodana");
        }
    }

    public int getBrojZadnjeDretve() {
        return brojZadnjeDretve;
    }

    public synchronized void setBrojZadnjeDretve(int brojZadnjeDretve) {
        this.brojZadnjeDretve = brojZadnjeDretve;
    }

    public long getUkupnoTrajanjeDretvi() {
        return ukupnoTrajanjeDretvi;
    }

    public synchronized void setUkupnoTrajanjeDretvi(long trajanjeZadnjeDretve) {
        this.ukupnoTrajanjeDretvi = this.ukupnoTrajanjeDretvi+trajanjeZadnjeDretve;
    }
    
}
