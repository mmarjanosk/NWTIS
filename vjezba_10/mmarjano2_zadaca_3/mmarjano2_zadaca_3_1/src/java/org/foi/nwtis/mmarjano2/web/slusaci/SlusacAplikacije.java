/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.slusaci;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.mmarjano2.KonfiguracijaBP;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mmarjano2.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.mmarjano2.web.SCHelper;
import org.foi.nwtis.mmarjano2.web.SqlRunner;
import org.foi.nwtis.mmarjano2.web.dretve.PozadinskaDretva;

/**
 * Lifecycle listener
 *
 * @author Matija Marjanović
 */
public class SlusacAplikacije implements ServletContextListener {
    
    PozadinskaDretva pd;
    
    /**
     * Context initialized listeneer
     * @param sce ServletContextEventsa
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        
        
        
        sce.getServletContext();
        ServletContext context = sce.getServletContext();
        String datoteka = context.getRealPath("/WEB-INF")
                + File.separator
                + context.getInitParameter("konfiguracija");
        KonfiguracijaBP bp_konf = new KonfiguracijaBP(datoteka);
        Konfiguracija konf = null;
        try {
            konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
           context.setAttribute("APIKey", konf.dajPostavku("apikey"));
            context.setAttribute("trajanjeDretve", konf.dajPostavku("intervalDretveZaMeteoPodatke"));
            SCHelper.setApikey(konf.dajPostavku("apikey"));
            SCHelper.setKonfBP(bp_konf);
            
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        context.setAttribute("BP_Konfig", bp_konf);
        String sql =  "Select Max(id) from uredaji";
        
        SqlRunner sqlc= new SqlRunner(context);
        ResultSet rs =sqlc.izvrsiSQL(sql);
        try {
             while (rs.next()){
                 String id = rs.getString(1);
                 context.setAttribute("MaxID", id);
            System.out.println("Uspješno "+ id);       
    } 

            System.out.println(context.getAttribute("APIKey"));
            System.out.println(context.getAttribute("MaxID"));
           
        } catch (SQLException ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pd = new PozadinskaDretva();
        pd.setSc(context);
        pd.start();
    }

    /**
     * Context destroyed listener metoda
     * @param sce ServletContextEvent 
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
             if (pd != null) {
            pd.interrupt();
        }
    }
}
