/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.slusaci;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.mmarjano2.konfiguracije.bp.BP_Konfiguracija;

/**
 * Web application lifecycle listener.
 *
 * @author Matija
 */
public class SlusacAplikacije implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       ServletContext sc =sce.getServletContext();
        String path = sc.getRealPath("/WEB-INF") + java.io.File.separator;
            String datoteka = path + sc.getInitParameter("konfiguracija");
            BP_Konfiguracija bp = new BP_Konfiguracija(datoteka);
            
            if (bp.getStatus()) {
                sc.setAttribute("BP_Konfiguracija", bp); //atribut u kojem je spremljen objekt konfiguracije
                
            }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
