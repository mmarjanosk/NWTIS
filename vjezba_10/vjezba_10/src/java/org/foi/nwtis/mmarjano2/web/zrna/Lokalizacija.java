/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.zrna;

import java.util.ArrayList;
import java.util.Locale;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.foi.nwtis.mmarjano2.web.kontrole.Izbornik;

/**
 *
 * @author Matija
 */
@Named(value = "lokalizator")
@Dependent
public class Lokalizacija {

    private static final ArrayList<Izbornik> izbornikJezika = new ArrayList<>();
    private String odabraniJezik;

    static {
        izbornikJezika.add(new Izbornik("hrvatski", "hr"));
        izbornikJezika.add(new Izbornik("engleski", "en"));
        izbornikJezika.add(new Izbornik("njemački", "de"));
    }

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

    public static ArrayList<Izbornik> getIzbornikJezika() {
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
