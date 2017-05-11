/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.kontrole;

import java.util.Date;

/**
 * Pomoćna klasa za pregled poruka
 *
 * @author Matija
 */
public class Poruka {

    private String id;
    private Date vrijemeSlanja;
    private Date vrijemePrijema;
    private String salje;
    private String predmet;
    private String sadrzaj;
    private String vrsta;

    /**
     * Konstruktor klase Poruka
     *
     * @param id - String id poruke
     * @param vrijemeSlanja - Date vrijeme slanje poruke
     * @param vrijemePrijema - Date vrijeme prijema poruke
     * @param salje -String adresa pošiljatelja
     * @param predmet - String predmet poruke
     * @param sadrzaj - String sadrzaj poruke
     * @param vrsta - String vrsta poruke
     */
    public Poruka(String id, Date vrijemeSlanja, Date vrijemePrijema, String salje, String predmet, String sadrzaj, String vrsta) {
        this.id = id;
        this.vrijemeSlanja = vrijemeSlanja;
        this.vrijemePrijema = vrijemePrijema;
        this.salje = salje;
        this.predmet = predmet;
        this.sadrzaj = sadrzaj;
        this.vrsta = vrsta;
    }

    /**
     * Getter metoda za id
     *
     * @return String id poruke
     */
    public String getId() {
        return id;
    }

    /**
     * Getter metoda za vrijeme slanja poruke
     *
     * @return Date vrijeme slanja
     */
    public Date getVrijemeSlanja() {
        return vrijemeSlanja;
    }

    /**
     * Getter metoda za vrijeme prijema
     *
     * @return Date vrijeme prijema
     */
    public Date getVrijemePrijema() {
        return vrijemePrijema;
    }

    /**
     * Getter za poredmet poruke
     *
     * @return String predmet poruke
     */
    public String getPredmet() {
        return predmet;
    }

    /**
     * Getter metoda za adresu posiljatelja poruke
     *
     * @return String adresa pošiljatelja
     */
    public String getSalje() {
        return salje;
    }

    /**
     * Getter metoda za vrstu poruke
     *
     * @return String vrsta poruke
     */
    public String getVrsta() {
        return vrsta;
    }

    /**
     * Getter metoda za sadržaj poruke
     *
     * @return String sadržaj poruke
     */
    public String getSadrzaj() {
        return sadrzaj;
    }

}
