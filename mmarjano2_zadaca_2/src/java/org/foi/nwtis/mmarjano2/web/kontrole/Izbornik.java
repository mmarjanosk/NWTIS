/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.kontrole;

/**
 * Pomoćna klasa za kreiranje izbornika
 *
 * @author Matija
 */
public class Izbornik {

    private String labela;
    private String vrijednost;
    private int brojPor;

    /**
     * Konstruktor klase Izbornika
     *
     * @param labela - labela izbornika
     * @param vrijednost - vrijednost izbornikaq
     */
    public Izbornik(String labela, String vrijednost) {
        this.labela = labela;
        this.vrijednost = vrijednost;
    }

    /**
     * Konstruktor za izbornik mapa
     *
     * @param labela -String labela mape
     * @param vrijednost - String vrijednost mape
     * @param brojPor - String broj poruka u mapi
     */
    public Izbornik(String labela, String vrijednost, int brojPor) {
        this.labela = labela;
        this.brojPor = brojPor;
        this.vrijednost = vrijednost;
    }

    /**
     * Getter metoda za labelua
     *
     * @return labela -String vraća labelu izbornika
     */
    public String getLabela() {
        return labela;
    }

    /**
     * Setter metoda za labelu izbornika
     *
     * @param labela - String labela izbornika
     */
    public void setLabela(String labela) {
        this.labela = labela;
    }

    /**
     * Getter metoda za vrijednost izbornika
     *
     * @return - String vrijednost izbornika
     */
    public String getVrijednost() {
        return vrijednost;
    }

    /**
     * Setter metoda za vrijednost izbornika
     *
     * @param vrijednost - String vrijednost izbornika
     */
    public void setVrijednost(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    /**
     * Getter metoda za broj poruka u mapi
     *
     * @return int broj poruka u mapi
     */
    public int getBrojPor() {
        return brojPor;
    }

    /**
     * Setter metoda za broj poruka u mapi
     *
     * @param brojPor int broj poruka u mapi
     */
    public void setBrojPor(int brojPor) {
        this.brojPor = brojPor;
    }

}
