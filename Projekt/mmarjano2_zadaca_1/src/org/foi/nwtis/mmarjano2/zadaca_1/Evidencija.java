/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matija
 * Klasa zadužena sa spremanje evidencije o stanju sustava i dretvi
 */
public class Evidencija implements Serializable {

    private int brojUspjesnihZahtjeva = 0;
    private int brojPrekinutihZahtjeva = 0;
    private Map<String, Integer> zahtjeviZaAdrese = new HashMap<>();
    private ArrayList adrese = new ArrayList();
    private int brojZadnjeDretve = 0;
    private long ukupnoTrajanjeDretvi = 0;
    private String ZadnjaAdresa = "";
    private int ukupnoZahtjeva = 0;

    
   
    
    /**
     * Getter za array listu adrese
     * @return array list adrese
     */
    public ArrayList getAdrese() {
        return adrese;
    }
    /**
     * Setter za array listu adrese
     * @param adresa -polje adrese koje se dodaje u arraylistu
     */
    public void setAdrese(String adresa) {
        
        if(!adrese.contains(adresa)){
            adrese.add(adresa);
        } else {
            
        }
    }

     /**
      * Getter za ukupnoZahtjeva
      * @return -int  ukupan broj zahtjeva
      */

    public int getUkupnoZahtjeva() {
        ukupnoZahtjeva = brojUspjesnihZahtjeva + brojPrekinutihZahtjeva;
        return ukupnoZahtjeva;
    }
    
    /**
     * Getter za ZadnjaAdresa
     * @return -string zadnja adresa s koje je poslan zahtjev
     */
    
    public String getZadnjaAdresa() {
        return ZadnjaAdresa;
    }
    
    /**
     * Setter za ZadnjaAdresa
     * @param ZadnjaAdresa -string zadnja adresa s koje je poslan zahtjev
     */
    public synchronized void setZadnjaAdresa(String ZadnjaAdresa) {
        this.ZadnjaAdresa = ZadnjaAdresa;
    }
    
    /**
     * Metoda koja vraća veličinu mape zahtjeviZaAdrese
     * @return -int veličina mape zahtjeviZaAdrese 
     */
    public int getSize() {
        return zahtjeviZaAdrese.size();
    }
    
    /**
     * Metoda koja provjerava nalazi li se adresa u nizu zahtjeviZaAdrese
     * @param adresa -string adresa koja se provjerava
     * @return - boolean postoji li ili ne
     */
    public boolean getState(String adresa) {
        return zahtjeviZaAdrese.containsKey(adresa);
    }
    
   /**
     * Metoda koja provjerava nalazi li se adresa u nizu zahtjeviZaAdrese
     * @param adresa -string adresa koja se provjerava
     * @return - boolean postoji li ili ne
     */
    public boolean contains(String adresa) {
        return zahtjeviZaAdrese.containsKey(adresa);
    }
    
    /**
     * Metoda koja vraća broj uspješnih zahtjeva
     * @return - int broj uspješnih zahtjeva
     */
    public int getBrojUspjesnihZahtjeva() {
        return brojUspjesnihZahtjeva;
    }
    /**
     * Metoda koja povećava broj uspješnih zahtjeva za 1
     */
    public synchronized void setBrojUspjesnihZahtjeva() {
        this.brojUspjesnihZahtjeva++;
    }
    
    /**
     * Metoda koja vraća broj prekinutih zahtjeva
     * @return - int broj prekinutih zahtjeva
     */
    public int getBrojPrekinutihZahtjeva() {
        return brojPrekinutihZahtjeva;
    }
    
    /**
     * Metoda koja povećava broj prekinutih zahtjevaa za 1
     */
    public synchronized void setBrojPrekinutihZahtjeva() {
        this.brojPrekinutihZahtjeva++;
    }
    
    /**
     * Metoda koja vraća broj zahtjeva poslanih s neke adrese
     * @param adresa - string ciljana adresa
     * @return -broj zahtjeva s ciljane adrese
     */
    public int getZahtjeviZaAdrese(String adresa) {
        return zahtjeviZaAdrese.get(adresa);
    }
    /**
     * Metoda koja provjerava postoji li adresa u mapi zahtjeviZaAdrese te ih
     * u skladu s tim ili dodaje i povećava broj zahtjeva s te adrese za 1 ili
     * dodaje adresu u listu adresa te postavlja broj zahtjeva na 0
     * @param adresa -string ciljana adresa
     */
    public synchronized void setZahtjeviZaAdrese(String adresa) {
        if (zahtjeviZaAdrese.containsKey(adresa)) {
            int value = zahtjeviZaAdrese.get(adresa) + 1;
            zahtjeviZaAdrese.put(adresa, value);
            System.out.println("Adresa updateana");
        } else {
            zahtjeviZaAdrese.put(adresa, 0);
            System.out.println("Adresa dodana");
        }
    }
    /**
     * Metoda koja vraća broj zadnje evidentirane radne dretve
     * @return - int broj zadnje aktivne dretve
     */
    public int getBrojZadnjeDretve() {
        return brojZadnjeDretve;
    }
    /**
     * Metoda koja postavlja broj zadnje evidentirane dretve
     * @param brojZadnjeDretve --int broj aktivne dretve
     */
    public synchronized void setBrojZadnjeDretve(int brojZadnjeDretve) {
        this.brojZadnjeDretve = brojZadnjeDretve;
    }
    /**
     * Metoda koja vraća ukupno trajanje svih evidentiranih dretvi
     * @return -long ukupno trajanje dretvi
     */
    public long getUkupnoTrajanjeDretvi() {
        return ukupnoTrajanjeDretvi;
    }
    
    /**
     * Metoda koja ažurira ukupno trajanje dretvi na temelju trajanja zadnje evidentirane aktivne dretvbe
     * @param trajanjeZadnjeDretve  - long trajanje zadnje evidentirane aktivne dretve
     */
    public synchronized void setUkupnoTrajanjeDretvi(long trajanjeZadnjeDretve) {
        this.ukupnoTrajanjeDretvi = this.ukupnoTrajanjeDretvi + trajanjeZadnjeDretve;
    }

}
