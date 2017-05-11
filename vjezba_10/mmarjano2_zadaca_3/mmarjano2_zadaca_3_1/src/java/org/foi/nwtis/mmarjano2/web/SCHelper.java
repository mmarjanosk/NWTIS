/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web;

import org.foi.nwtis.mmarjano2.KonfiguracijaBP;

/**
 * Pomoćna monitor klasa za dohvaćanje konfiguracije
 * @author Matija Marjanović
 */
public class SCHelper {

    /**
     * Konstruktor klase SCHelpler
     */
    public SCHelper() {
    }
    
    
    private static KonfiguracijaBP konfBP;
    private static String apikey;
   
    /**
     * Getter za KonfiguracijuBP
     * @return KonfiguracijaBP konfiguracija baze
     */
    public synchronized static KonfiguracijaBP getKonfBP() {
        return konfBP;
    }

    /**
     * Setter za KonfiguracijuBP
     * @param konfBP KonfiguracijaBP konfiguracija baze
     */
    public synchronized static void setKonfBP(KonfiguracijaBP konfBP) {
        SCHelper.konfBP = konfBP;
    }

    /**
     * Getter za APIKey
     * @return String API key
     */
    public synchronized static String getApikey() {
        return apikey;
    }

    /**
     * Setter za APIKey
     * @param apikey STring API key
     */
    public synchronized static void setApikey(String apikey) {
        SCHelper.apikey = apikey;
    }   
    
}
