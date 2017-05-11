/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.dretve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.foi.nwtis.mmarjano2.KonfiguracijaBP;
import org.foi.nwtis.mmarjano2.rest.klijenti.OWMKlijent;
import org.foi.nwtis.mmarjano2.web.podaci.MeteoPodaci;

/**
 * Pozadinska dretva koja izvršava dohvaćanje meteo podataka za sve uređaje i 
 * njohvo spremanje u bazu
 * @author Matija Marjanović
 */
public class PozadinskaDretva extends Thread {

    ServletContext sc;

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        
        KonfiguracijaBP BPkonf = (KonfiguracijaBP) sc.getAttribute("BP_Konfig");

        String connURL = BPkonf.getServerDatabase() + BPkonf.getUserDatabase();
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        String ua = BPkonf.getUserUsername();
        String ap = BPkonf.getUserPassword();
        String apikey = sc.getAttribute("APIKey").toString();
        OWMKlijent owmk = new OWMKlijent(apikey);
        long trajanjeObrade;

        try {
            Class.forName(BPkonf.getDriverDatabase());
        } catch (ClassNotFoundException ex) {
            System.out.println("Nemoguće čitati driver" + ex.getMessage());
            return;
        }

        while (true) {

            String sql = "Select * from uredaji";
            long vrijemePocetka = System.currentTimeMillis();

            try {
                conn = DriverManager.getConnection(connURL, ua, ap);
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(sql);

               
              
                    while (resultSet.next()) {

                        String id = resultSet.getString("id");
                        String adresa = resultSet.getString("naziv");

                        String longitude = resultSet.getString("longitude");
                        String latitude = resultSet.getString("latitude");

                        MeteoPodaci mp = owmk.getRealTimeWeather(latitude, longitude);

                        int vrijeme = mp.getWeatherNumber();
                        String vrijemeopis = mp.getWeatherValue();
                        
                        if(vrijemeopis.length()>24){
                           vrijemeopis= vrijemeopis.substring(0,24);
                        }
                        Float temp = mp.getTemperatureValue();
                        Float tempMin = mp.getTemperatureMin();
                        Float tempMax = mp.getTemperatureMax();
                        Float vlaga = mp.getHumidityValue();
                        Float tlak = mp.getPressureValue();
                        Float vjetar = mp.getWindSpeedValue();
                        Float vjetarSmjer = mp.getWindDirectionValue();

                        sql = "Insert into meteo (ID,ADRESASTANICE,LATITUDE,LONGITUDE,VRIJEME,VRIJEMEOPIS,TEMP,TEMPMIN,TEMPMAX,VLAGA,TLAK,VJETAR,VJETARSMJER) values ("
                                + id + ","
                                + "\'" + adresa + "\'" + ","
                                + latitude + ","
                                + longitude + ","
                                + "\'" + vrijeme + "\'" + ","
                                + "\'" + vrijemeopis + "\'" + ","
                                + temp + ","
                                + tempMin + ","
                                + tempMax + ","
                                + vlaga + ","
                                + tlak + ","
                                + vjetar + ","
                                + vjetarSmjer+  ")";
                        System.out.println(sql);
                        stmt = conn.createStatement();
                        stmt.execute(sql);
                    }
                    trajanjeObrade = (int) System.currentTimeMillis() - (int) vrijemePocetka;

                    String trajanjeDretv = (String) sc.getAttribute("trajanjeDretve");
                    long trajanjeDretve = Long.valueOf(trajanjeDretv);
                    System.out.println(trajanjeDretve);
                    System.out.println(trajanjeObrade);
                    sleep(trajanjeDretve * 1000 - trajanjeObrade);
                

            } catch (SQLException ex) {
                Logger.getLogger(PozadinskaDretva.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(PozadinskaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Setter za ServletContext
     * @param sc ServletContext
     */
    public void setSc(ServletContext sc) {
        this.sc = sc;
    }
}
