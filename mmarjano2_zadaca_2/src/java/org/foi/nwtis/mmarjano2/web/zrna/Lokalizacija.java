/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import javax.ejb.Local;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.context.FacesContext;
import org.foi.nwtis.mmarjano2.web.kontrole.Izbornik;

/**
 * Zrno za lokalizaciju, tj promjenu jezika UI-a
 *
 * @author Matija
 */
@Named(value = "lokalizator")
@SessionScoped
public class Lokalizacija implements Serializable {

    private static final ArrayList<Izbornik> izbornikJezika = new ArrayList<>();

    private String odabraniJezik = "hr";

    static {
        izbornikJezika.add(new Izbornik("hrvatski", "hr"));
        izbornikJezika.add(new Izbornik("engleski", "en"));
        izbornikJezika.add(new Izbornik("njemački", "de"));
    }

    /**
     * Konstruktor zrna Lokalizacija
     */
    public Lokalizacija() {

    }

    /**
     * Getter metoda za odabrani jezik
     *
     * @return String odabrani jezik
     */
    public String getOdabraniJezik() {
        //FacesContext FC = FacesContext.getCurrentInstance();
        UIViewRoot UVIR = FacesContext.getCurrentInstance().getViewRoot();
        if (UVIR != null) {
            Locale lokalniJezik = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            odabraniJezik = lokalniJezik.getLanguage();
        }
        return odabraniJezik;
    }

    /**
     * Setter metoda za odabrani jezik
     *
     * @param odabraniJezik odabrani jezik
     */
    public void setOdabraniJezik(String odabraniJezik) {
        this.odabraniJezik = odabraniJezik;
        Locale lokalniJezik = new Locale(odabraniJezik);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(lokalniJezik);
    }

    /**
     * Getter metoda za izbornik jezika
     *
     * @return ArrayList izbornika jezika
     */
    public ArrayList<Izbornik> getIzbornikJezika() {
        return izbornikJezika;
    }

    /**
     * Metoda za odabiranje jezika
     *
     * @return Object
     */
    public Object odaberiJezik() {
        setOdabraniJezik(odabraniJezik);
        return "PromjenaJezika";
    }

    /**
     * Metoda koja vraća objekt za slanje poruka
     *
     * @return Object
     */
    public Object saljiPoruku() {
        return "saljiPoruku";
    }

    /**
     * Metoda koja vrtaćća objekt za pregled poruka
     *
     * @return Object
     */
    public Object pregledPoruka() {
        return "pregledPoruka";
    }
}
