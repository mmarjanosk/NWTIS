/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.klijenti;

import org.foi.nwtis.mmarjano2.web.klijent.MeteoPodaci;

/**
 *
 * @author Matija
 */
public class MeteoWSKlijent {

    /**
     *Web service caller za metodu dajSveuredaje
     * @return List Ureddjaj
     */
    public static java.util.List<org.foi.nwtis.mmarjano2.web.klijent.Uredjaj> dajSveuredaje() {
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service service = new org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service();
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveuredaje();
    }

    /** 
     * Web service caller za metodu dodajUredjaj
     * @param naziv String naziv uredaja
     * @param adresa String adresa uredaja
     */
    public static void dodajUredaj(java.lang.String naziv, java.lang.String adresa) {
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service service = new org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service();
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS port = service.getGeoMeteoWSPort();
        port.dodajUredaj(naziv, adresa);
    }

    /**
     * Web service caller za metodu dajSveMeteoPodatkeZaUredjaj
     * @param id int id uredaja
     * @param from long vrijeme od
     * @param to long vrijeme do
     * @return List MeteoPodaci
     */
    public static java.util.List<org.foi.nwtis.mmarjano2.web.klijent.MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(int id, long from, long to) {
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service service = new org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service();
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveMeteoPodatkeZaUredjaj(id, from, to);
    }

    /**
     * Web service caller za metodu minMaxTemp
     * @param id int id uredaja
     * @param from long vijeme od
     * @param to long vrijeme do
     * @return
     */
    public static java.util.List<java.lang.Float> minMaxTemp(int id, long from, long to) {
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service service = new org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service();
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.minMaxTemp(id, from, to);
    }

    /**
     * Web service caller za metodu minMaxVlaga
     * @param id int id uredaja
     * @param from long vijeme od
     * @param to long vrijeme do
     * @return
     */
    public static java.util.List<java.lang.Float> minMaxVlaga(int id, long from, long to) {
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service service = new org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service();
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.minMaxVlaga(id, from, to);
    }

    /**
     * Web service caller za metodu minMaxTlak
     * @param id int id uredaja
     * @param from long vijeme od
     * @param to long vrijeme do
     * @return
     */
    public static java.util.List<java.lang.Float> minMaxTlak(int id, long from, long to) {
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service service = new org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service();
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.minMaxTlak(id, from, to);
    }

    /**
     *Web service caller za metodu dajPosljednjePodatke
     * @param id int id uredaja
     * @return MeteoPodaci objekt
     */
    public static MeteoPodaci dajPosljednjePodatke(int id) {
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service service = new org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS_Service();
        org.foi.nwtis.mmarjano2.web.klijent.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajPosljednjePodatke(id);
    }

    
    
    
    
    
    
}
