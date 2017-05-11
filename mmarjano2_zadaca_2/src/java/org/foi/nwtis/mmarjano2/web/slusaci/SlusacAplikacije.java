/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mmarjano2.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.mmarjano2.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.mmarjano2.web.dretve.ObradaPoruka;

/**
 * Slusac aplikacije
 *
 * @author Matija
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {

    private ObradaPoruka op = null;

    /**
     * Metoda koja postavlja context atribute za datoteke konfiguracije baze i
     * konfiguracije mail servera i pokreće dretvu obrade poruka
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String datoteka = context.getRealPath("/WEB-INF")
                + File.separator
                + context.getInitParameter("konfiguracija");

        BP_Konfiguracija bp_konf = new BP_Konfiguracija(datoteka);
        context.setAttribute("BP_Konfig", bp_konf);
        System.out.println("Učitana konfiguacija");

        Konfiguracija konf = null;
        try {
            konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            context.setAttribute("Mail_Konfig", konf);
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }

        op = new ObradaPoruka();
        op.setSc(context);
        op.start();
    }

    /**
     * Metoda koja prekida dretvu usred gašenja aplikacije
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (op != null) {
            op.interrupt();
        }
    }
}
