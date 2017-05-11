/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.ws.serveri;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.mmarjano2.web.podaci.MeteoPodaci;
import org.foi.nwtis.mmarjano2.web.podaci.Uredjaj;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.foi.nwtis.mmarjano2.rest.klijenti.GMKlijent;
import org.foi.nwtis.mmarjano2.rest.klijenti.OWMKlijent;
import org.foi.nwtis.mmarjano2.web.DodajUredaj;
import org.foi.nwtis.mmarjano2.web.SqlRunner;
import org.foi.nwtis.mmarjano2.web.podaci.Lokacija;

/**
 * SOAP MeteoWS
 * @author Matija Marjanović
 */
@WebService(serviceName = "GeoMeteoWS")

public class GeoMeteoWS {

    @Resource
    private WebServiceContext context;

    DodajUredaj dodrd = new DodajUredaj();

    /**
     * Web service operacija za dohvaćanje svih uređaja
     * @return List Uredjaj
     */
    @WebMethod(operationName = "dajSveuredaje")
    public java.util.List<Uredjaj> dajSveuredaje() {

        ServletContext servletContext = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        System.out.println(servletContext.getInitParameter("APIKey"));

        SqlRunner se = new SqlRunner(servletContext);

        String sql = "Select * from uredaji ";
        List<Uredjaj> uredaji = new ArrayList<>();
        ResultSet resultSet = se.izvrsiSQL(sql);
        boolean flag = true;

        while (true) {
            try {
                while (resultSet.next()) {

                    String adresa = resultSet.getString("naziv");
                    int id = Integer.valueOf(resultSet.getString("id"));
                    Uredjaj ur = new Uredjaj();
                    ur.setNaziv(adresa);
                    ur.setId(id);
                    uredaji.add(ur);
                }
            } catch (SQLException ex) {
                Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
        }
        return uredaji;
    }

    /**
     * Web service operacija za dohvaćanje meteo podataka za specifičan uređaj
     * @param id int id uređaja
     * @param to long vrijeme do
     * @param from long vrijeme od
     * @return List MeteoPodaci
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaUredjaj")
    public List<MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(@WebParam(name = "id") int id,
            @WebParam(name = "from") long from,
            @WebParam(name = "to") long to
    ) {
        try {
            ArrayList<MeteoPodaci> mp = new ArrayList<>();
            Timestamp from2 = new Timestamp(from);
            Timestamp to2 = new Timestamp(to);
            ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
            String sql = "Select * from meteo where id=" + id + " and preuzeto between " + "\'" + from2 + "\'" + " and " + "\'" + to2 + "\'";
            System.out.println(sql);
            SqlRunner sqr = new SqlRunner(sc);
            ResultSet rs = sqr.izvrsiSQL(sql);
            while (rs.next()) {
                MeteoPodaci meteoPodaci = new MeteoPodaci();
                meteoPodaci.setWeatherNumber(Integer.parseInt(rs.getString("Vrijeme").trim(), 16 ));
                meteoPodaci.setWeatherValue(rs.getString("vrijemeopis"));
                meteoPodaci.setTemperatureValue(Float.valueOf(rs.getString("temp")));
                meteoPodaci.setTemperatureMin(Float.valueOf(rs.getString("tempmin")));
                meteoPodaci.setTemperatureMax(Float.valueOf(rs.getString("tempmax")));
                meteoPodaci.setHumidityValue(Float.valueOf(rs.getString("vlaga")));
                meteoPodaci.setPressureValue(Float.valueOf(rs.getString("tlak")));
                meteoPodaci.setWindSpeedValue(Float.valueOf(rs.getString("vjetar")));
                meteoPodaci.setWindDirectionValue(rs.getFloat("vjetarsmjer"));
                try {

                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(rs.getString("preuzeto"));
                    meteoPodaci.setLastUpdate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
                }
                mp.add(meteoPodaci);
            }

            return mp;
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Web service operacija za dodavanje IOT uređaja
     * @param naziv String naziv uređaja
     * @param adresa String adresa uređaja
     */
    @WebMethod(operationName = "dodajUredaj")
    public void dodajUredaj(@WebParam(name = "naziv") String naziv, @WebParam(name = "adresa") String adresa) {
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        SqlRunner se = new SqlRunner(sc);
        GMKlijent gmk = new GMKlijent();
        Lokacija l = gmk.getGeoLocation(adresa);
        String sql;
        String latitude = l.getLatitude();
        String longitude = l.getLongitude();
        String id = (String) sc.getAttribute("MaxID");
        int MXid = Integer.valueOf(id);
        MXid++; 
        sc.setAttribute("MaxID", String.valueOf(MXid));
        sql = "insert into uredaji (id,naziv,latitude,longitude) values(" + MXid + ",\'" + naziv + "\'," + latitude + "," + longitude + ")";
        System.out.println(sql);
        se.doSQL(sql);
    }

    /**
     * Web service operacija za dohvaćanje minimalne i maksimalne temp u intervalu
     * @param id int id uređaja 
     * @param to long vrijeme do
     * @param from long vrijeme od
     * @return List Float
     */
    @WebMethod(operationName = "minMaxTemp")
    public List<Float> minMaxTemp(@WebParam(name = "id") int id, @WebParam(name = "from") long from, @WebParam(name = "to") long to) {
        ArrayList<Float> mM = new ArrayList<>();
        Timestamp from2 = new Timestamp(from);
        Timestamp to2 = new Timestamp(to);
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        String sql = "Select MAX(temp),MIN(temp) from meteo where id=" + id + " and preuzeto between " + "\'" + from2 + "\'" + " and " + "\'" + to2 + "\'";
        System.out.println(sql);
        SqlRunner sqr = new SqlRunner(sc);
        ResultSet rs = sqr.izvrsiSQL(sql);
        try {
            while (rs.next()) {
                mM.add(Float.valueOf(rs.getString(1)));
                mM.add(Float.valueOf(rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mM;
    }

     /**
     * Web service operacija za dohvaćanje minimalnu i maksimalnu vlage u intervalu
     * @param id int id uređaja 
     * @param to long vrijeme do
     * @param from long vrijeme od
     * @return List Float
     */
    @WebMethod(operationName = "minMaxVlaga")
    public List<Float> minMaxVlaga(@WebParam(name = "id") int id, @WebParam(name = "from") long from, @WebParam(name = "to") long to) {
        ArrayList<Float> vlaga = new ArrayList<>();
        Timestamp from2 = new Timestamp(from);
        Timestamp to2 = new Timestamp(to);
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        String sql = "Select MAX(vlaga),MIN(vlaga) from meteo where id=" + id + " and preuzeto between " + "\'" + from2 + "\'" + " and " + "\'" + to2 + "\'";
        System.out.println(sql);
        SqlRunner sqr = new SqlRunner(sc);
        ResultSet rs = sqr.izvrsiSQL(sql);
        try {
            while (rs.next()) {
                vlaga.add(Float.valueOf(rs.getString(1)));
                vlaga.add(Float.valueOf(rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vlaga;
    }

    /**
     * Web service operacija za dohvaćanje minimalni i maksimalni tlak u intervalu
     * @param id int id uređaja 
     * @param to long vrijeme do
     * @param from long vrijeme od
     * @return List Float
     */
    @WebMethod(operationName = "minMaxTlak")
    public List<Float> minMaxTlak(@WebParam(name = "id") int id, @WebParam(name = "from") long from, @WebParam(name = "to") long to) {
        ArrayList<Float> tlak = new ArrayList<>();
        Timestamp from2 = new Timestamp(from);
        Timestamp to2 = new Timestamp(to);
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        String sql = "Select MAX(tlak),MIN(tlak) from meteo where id=" + id + " and preuzeto between " + "\'" + from2 + "\'" + " and " + "\'" + to2 + "\'";
        System.out.println(sql);
        SqlRunner sqr = new SqlRunner(sc);
        ResultSet rs = sqr.izvrsiSQL(sql);
        try {
            while (rs.next()) {
                tlak.add(Float.valueOf(rs.getString(1)));
                tlak.add(Float.valueOf(rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tlak;
    }

    /**
     * Web service operacija za dohvaćanje važećih meteo podataka
     * @param id int id uređaja
     * @return  MeteoPodaci objekt
     */
    @WebMethod(operationName = "dajPosljednjePodatke")
    public MeteoPodaci dajPosljednjePodatke(@WebParam(name = "id") int id) {
        try {
            String latitude = "";
            String longitude="";
            ArrayList<MeteoPodaci> mp = new ArrayList<>();
            ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
             String sql = "Select latitude,longitude from uredaji where id="+id;
             SqlRunner sqr = new SqlRunner(sc);
             ResultSet rs = sqr.izvrsiSQL(sql);
             while(rs.next()){
                 latitude=rs.getString("latitude");
                 longitude=rs.getString("longitude");
             }
             
             OWMKlijent owm = new OWMKlijent(sc.getAttribute("APIKey").toString());
             
             

            return owm.getRealTimeWeather(latitude, longitude);
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
