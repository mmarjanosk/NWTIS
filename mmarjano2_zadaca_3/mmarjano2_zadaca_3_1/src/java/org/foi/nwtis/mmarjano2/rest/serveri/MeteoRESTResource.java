/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.rest.serveri;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.foi.nwtis.mmarjano2.rest.klijenti.OWMKlijent;
import org.foi.nwtis.mmarjano2.web.SCHelper;
import org.foi.nwtis.mmarjano2.web.SqlRunner;
import org.foi.nwtis.mmarjano2.web.podaci.MeteoPodaci;

/**
 * REST Web Service za dohvaćanje meteoroloških podataka
 *
 * @author Matija Marjanović
 */

public class MeteoRESTResource {
    
    @Context
    private UriInfo context;

    @javax.ws.rs.core.Context
    ServletContext scontext;
    
    private String id;
    
    
    /**
     * Konstruktor MeteoRESTResourcea
     * @param id  String id servisa
     */
    private MeteoRESTResource(String id) {
        this.id = id;
       
    }

    /**
     * Get instance of the MeteoRESTResource
     * @param id String id instance
     * @return MeteoRESTResource instannca
     */
    public static MeteoRESTResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MeteoRESTResource class.
        return new MeteoRESTResource(id);
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.mmarjano2.rest.serveri.MeteoRESTResource
     *
     * @return an instance of java.lang.String
     */
    
    
    
   
    
    
    /**
     * Metoda za dohvaćanje meteo podataka uređaja na temelju njegovog id-a
     * @return JSON String podatak
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson2() {
       
       
    
      
        try {
          
           

         String latitude = "";
            String longitude="";
            ArrayList<MeteoPodaci> mp = new ArrayList<>();
           
             String sql = "Select latitude,longitude from uredaji where id="+id;
             SqlRunner sqr = new SqlRunner(scontext);
             ResultSet rs = sqr.izvrsiSQL(sql);
             while(rs.next()){
                 latitude=rs.getString("latitude");
                 longitude=rs.getString("longitude");
             }
             
             OWMKlijent owm = new OWMKlijent(SCHelper.getApikey().toString());
             mp.add(owm.getRealTimeWeather(latitude, longitude));
     
        for (MeteoPodaci meteoPodaci :mp) {
            
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("Vrijeme", meteoPodaci.getWeatherIcon());
            job.add("VrijemeOpis", meteoPodaci.getWeatherValue());
            job.add("Temperatura", meteoPodaci.getTemperatureValue());
            job.add("TemperaturaMin", meteoPodaci.getTemperatureMin());
            job.add("TemperaturaMax", meteoPodaci.getTemperatureMax());
            job.add("Vlažnost", meteoPodaci.getHumidityValue());
            job.add("Tlak", meteoPodaci.getPressureValue());
            
            String S = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(meteoPodaci.getLastUpdate());
            System.out.println(S);
            
            job.add("Preuzeto",S);
            return job.build().toString();
            
        }
        
        

       

           
        } catch (SQLException ex) {
            Logger.getLogger(MeteoRESTResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
         return "";
    }

    /**
     * PUT method for updating or creating an instance of MeteoRESTResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource MeteoRESTResource
     */
    @DELETE
    public void delete() {
    }
}
