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
 *
 * @author grupa_2
 */
@Named(value = "lokalizator")
@SessionScoped
public class Lokalizacija implements Serializable {

    private static final ArrayList<Izbornik> izbornikJezika = new ArrayList<>();

    private String odabraniJezik="hr";
    
    static {
        izbornikJezika.add(new Izbornik("hrvatski", "hr"));
        izbornikJezika.add(new Izbornik("engleski", "en"));
        izbornikJezika.add(new Izbornik("njemački", "de"));
    }
    
    /**
     * Creates a new instance of Lokalizacija
     */
    public Lokalizacija() {
        
    }

    public String getOdabraniJezik() {
        //FacesContext FC = FacesContext.getCurrentInstance();
        UIViewRoot UVIR = FacesContext.getCurrentInstance().getViewRoot();
        if(UVIR != null){
            Locale lokalniJezik = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            odabraniJezik = lokalniJezik.getLanguage();
        }
        return odabraniJezik;
    }

    public void setOdabraniJezik(String odabraniJezik) {
        this.odabraniJezik = odabraniJezik;
        Locale lokalniJezik = new Locale(odabraniJezik);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(lokalniJezik);
    }
    
    public ArrayList<Izbornik> getIzbornikJezika() {
        return izbornikJezika;
    }
    
    public Object odaberiJezik() {
        setOdabraniJezik(odabraniJezik);
        return "PromjenaJezika";
    }
    public Object saljiPoruku() {
        return "saljiPoruku";
    }
    public Object pregledPoruka() {
        return "pregledPoruka";
    }
}
