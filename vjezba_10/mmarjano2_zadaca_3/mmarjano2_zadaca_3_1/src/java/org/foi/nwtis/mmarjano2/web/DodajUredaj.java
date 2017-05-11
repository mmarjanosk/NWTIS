/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web;

import java.io.IOException;
import java.nio.charset.Charset;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.mmarjano2.rest.klijenti.GMKlijent;
import org.foi.nwtis.mmarjano2.rest.klijenti.OWMKlijent;
import org.foi.nwtis.mmarjano2.web.podaci.Lokacija;
import org.foi.nwtis.mmarjano2.web.podaci.MeteoPodaci;

/**
 *
 * @author  Matija Marjanović
 * 
 */
@WebServlet(name = "DodajUredaj", urlPatterns = {"/DodajUredaj"})
public class DodajUredaj extends HttpServlet {

    String naziv;
    String adresa;
    String akcija;
    String sql;
    String latitude;
    String longitude;
    ServletContext sc;
    HttpServletRequest request;
    HttpServletResponse response;
    String API;
    String MaxID;
    String lokacija;
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * Metoda za procesuiranje HTTP request parametara i pozivanje odgovarajuće
     * metode u skladu s njom
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        naziv = request.getParameter("naziv");
        adresa = request.getParameter("adresa");
        adresa = enkoder(adresa);
        naziv = enkoder(naziv);
        sc = request.getServletContext();
        API = sc.getAttribute("APIKey").toString();
        MaxID = sc.getAttribute("MaxID").toString();

        this.request = request;
        this.response = response;
        if(!naziv.isEmpty() && !adresa.isEmpty()){
            if (request.getParameter("geolokacija") != null) {
            akcija = "geolokacija";
        } else if (request.getParameter("spremi") != null) {
            akcija = "spremi";
        } else if (request.getParameter("meteoPodaci") != null) {
            akcija = "meteopodaci";
        }

        switch (this.akcija) {
            case "geolokacija":
                geoLokacija(naziv, adresa);
                break;

            case "spremi":
                spremi(naziv, adresa);
                break;

            case "meteopodaci":
                meteoPodaci(naziv, adresa);
                break;
        }
        
        }
        else {
                String error = "Niste upisali naziv ili adresu" ;
                 request.setAttribute("error", error);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Metoda za pribavljanje geolokacije te pozivanje metoda za dohvaćanje meteo
     * podataka i spremanje uređaja u bazu
     * @param naziv
     * @param adresa
     * @throws ServletException
     * @throws IOException
     */
    public void geoLokacija(String naziv, String adresa) throws ServletException, IOException {

        Charset.forName("UTF-8").encode(adresa);

        System.out.println("Adresa:" + adresa);

        GMKlijent gmk = new GMKlijent();
        Lokacija l = gmk.getGeoLocation(adresa);
        Float latitude = Float.valueOf(l.getLatitude());
        Float longitude = Float.valueOf(l.getLongitude());

      

        meteoPodaci(naziv, adresa);
        spremi(naziv, adresa);

    }

    /**
     *Metoda za spremanje IOT uređaja
     * @param naziv STring naziv IOT uređaja
     * @param adresa String adresa IOT uređaja
     */
    public void spremi(String naziv, String adresa) {
        try {
            SqlRunner se = new SqlRunner(sc);
            GMKlijent gmk = new GMKlijent();
            Lokacija l = gmk.getGeoLocation(adresa);
            String latitude = l.getLatitude();
            String longitude = l.getLongitude();
            String id = (String) sc.getAttribute("MaxID");
            int MXid = Integer.valueOf(id);
            MXid++;
            sql = "insert into uredaji (id,naziv,latitude,longitude) values(" + MXid + ",\'" + naziv + "\'," + latitude + "," + longitude + ")";
            System.out.println(sql);
            se.doSQL(sql);
            sc.setAttribute("MaxID", String.valueOf(MXid));
            System.out.println("Uspješno dodan uređaj");
            request.setAttribute("error", naziv+" je uspješno dodan");
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            
            request.setAttribute("error", "Pogreška pri dodavanju"+naziv);
            try {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(DodajUredaj.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DodajUredaj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *Metoda za dohvaćanje meteo podataka
     * @param naziv naziv IOT uređaja
     * @param adresa adresa IOT uređaja
     * @throws ServletException
     * @throws IOException
     */
    public void meteoPodaci(String naziv, String adresa) throws ServletException, IOException {

        GMKlijent gmk = new GMKlijent();
        Lokacija l = gmk.getGeoLocation(adresa);
        String latitude = l.getLatitude();
        String longitude = l.getLongitude();
        System.out.println(latitude);
        System.out.println(longitude);
        OWMKlijent owmk = null;
        String error;

        try {
            owmk = new OWMKlijent(this.API);
            MeteoPodaci mp = owmk.getRealTimeWeather(latitude, longitude);
            String temp = mp.getTemperatureValue().toString();
            String vlaga = mp.getHumidityValue().toString();
            String tlak = mp.getPressureValue().toString();
            String lokacija ="Longitude:"+longitude + " Latitude: "+latitude;
            System.out.println("Tempica je " + temp);
            System.out.println("Vlagica je " + vlaga);
            System.out.println("Tlak je " + tlak);
            lokacija ="Latitude:" + latitude + "  Longitude: " + longitude;

            request.setAttribute("temp", temp);
            request.setAttribute("vlaga", vlaga);
            request.setAttribute("tlak", tlak);
            request.setAttribute("lokacija", lokacija);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("OWMKlijent je vratio nepotpune podatke");
            request.setAttribute("error", "OWMKlijent je vratio nepotpune podatke");
            request.getRequestDispatcher("index.jsp").forward(request, response);

        }

    }

    /**
     * Pomoćna metoda za enkodiranje specijalnih znakova na UTF-8
     * @param input STring tekst za enkodiranje
     * @return String enkodirana vrijednost
     */
    public String enkoder(String input) {

        byte[] ptext = input.getBytes(ISO_8859_1);
        String value = new String(ptext, UTF_8);

        return value;
    }

}
