/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.zrna;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import org.foi.nwtis.mmarjano2.web.SlanjeMaila;

/**
 * Zrno za slanje poruka
 *
 * @author Matija
 */
@Named(value = "slanjePoruke")
@RequestScoped
public class SlanjePoruke {

    private String posluzitelj;
    private String salje;
    private String prima;
    private String predmet;
    private String sadrzaj;

    /**
     * Konstruktor klase SlanjePoruke
     */
    public SlanjePoruke() {
    }

    /**
     * Getter metoda za adresu pošiljatelja
     *
     * @return String adresa pošiljatelja
     */
    public String getSalje() {
        return salje;
    }

    /**
     * Setter metoda za adresu pošiljateljad
     *
     * @param salje String adresa pošiljatelja
     */
    public void setSalje(String salje) {
        this.salje = salje;
    }

    /**
     * Getter metoda za adresu primatelja
     *
     * @return String adresa primatelja
     */
    public String getPrima() {
        return prima;
    }

    /**
     * Setter metoda za adresu primatelja
     *
     * @param prima String adresa primatelja
     */
    public void setPrima(String prima) {
        this.prima = prima;
    }

    /**
     * Getter metoda za predmet poruke
     *
     * @return String predmet poruke
     */
    public String getPredmet() {
        return predmet;
    }

    /**
     * Setter metoda za predmet poruke
     *
     * @param predmet String predmet poruke
     */
    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    /**
     * Getter metoda za sadržaj poruke
     *
     * @return String sadržaj poruke
     */
    public String getSadrzaj() {
        return sadrzaj;
    }

    /**
     * Setter metoda za sadržaj poruke
     *
     * @param sadrzaj - String sadržaj poruke
     */
    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    /**
     * Metoda za slanje složene poruke
     *
     * @return String Poslana poruka
     */
    public String saljiPoruku() {
        try {
            //TODO dodaj za slanje poruke prema primjeru s predavanja koji je priložen uz zadaću
            SlanjeMaila sm = new SlanjeMaila();
            sm.salji(salje, prima, predmet, sadrzaj);

        } catch (MessagingException ex) {
            Logger.getLogger(SlanjePoruke.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "PoslanaPoruka";
    }

    /**
     * Metoda koja označava pocetnu stranicu
     *
     * @return
     */
    public String pocetna() {
        return "Pocetna";
    }

    /**
     * Metoda za navigaciju na pregleda poruka
     *
     * @return
     */
    public String pregledPoruka() {
        return "PregledPoruka";
    }

}
