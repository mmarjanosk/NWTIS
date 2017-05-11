/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.klijenti;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.foi.nwtis.mmarjano2.web.klijent.Uredjaj;
import org.foi.nwtis.mmarjano2.web.zrna.OdabirUredjaja;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Jersey REST client generated for REST resource:MeteoRESTResourceContainer
 * [/meteoREST]<br>
 * USAGE:
 * <pre>
 *        MeteoRestC client = new MeteoRestC();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Matija Marjanovioć
 */
public class MeteoRestC {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8084/mmarjano2_zadaca_3_1/webresources";

    /**
     * Konstruktor REST klijenta
     */
    public MeteoRestC() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("meteoREST");
    }

    /**
     * Metoda za slanje naziva i adrese uređaja koji treba dodati
     * @param naziv String naziv IOT uređaja
     * @param adresa String adresa IOT uređaja
     * @return Response
     * @throws ClientErrorException
     */
    public Response postJson(String naziv,String adresa) throws ClientErrorException {
        
        JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("uid",naziv);
            job.add("naziv",adresa);  
        
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(job.build().toString(), javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    /**
     * Metoda za slanje zahtjeva za dohvaćanjem popisa IOT uređaja
     * @return List Uredjaj
     * @throws ClientErrorException
     */
    public List<Uredjaj> getJson() throws ClientErrorException {
        WebTarget resource = webTarget;
        String odgovor = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
         List<Uredjaj> ura = new ArrayList<>();
      
        try {
            JSONArray ja = new  JSONArray(odgovor);
            for(int i=0;i<ja.length();i++){
                JSONObject job =  ja.getJSONObject(i);
                Uredjaj ur = new Uredjaj();
                ur.setId(Integer.valueOf(job.get("uid").toString()));
                ur.setNaziv(job.get("naziv").toString());
                ura.add(ur);
            }
            
            
        } catch (JSONException ex) {
            Logger.getLogger(OdabirUredjaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return ura;
    }

    /**
     * Metoda za zatvaranje klijenta
     */
    public void close() {
        client.close();
    }
    
}
