/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.klijenti;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.foi.nwtis.mmarjano2.web.klijent.MeteoPodaci;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Jersey REST client generated for REST resource:MeteoRESTResource<br>
 * USAGE:
 * <pre>
 *        MeteoRestKlijent client = new MeteoRestKlijent();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Matija Marjanović
 */
public class MeteoRestKlijent {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8084/mmarjano2_zadaca_3_1/webresources";

    /**
     * Konstruktor klijenta REST servisa
     * @param id int id  uređaja
     */
    public MeteoRestKlijent(String id) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        String resourcePath = java.text.MessageFormat.format("meteoREST/{0}", new Object[]{id});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    /**
     * Metoda za postavljanje url patha
     * @param id int id uređaja
     */
    public void setResourcePath(String id) {
        String resourcePath = java.text.MessageFormat.format("meteoREST/{0}", new Object[]{id});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    /**
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void putJson(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Metoda za slanje requesta prema REST servisu za dohvaćanje IOT uređaja
     * @return MeteoPodaci objekt
     * @throws ClientErrorException
     */
    public MeteoPodaci getJson2() throws ClientErrorException {
        MeteoPodaci mp = new MeteoPodaci();
        WebTarget resource = webTarget;
        String json = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
        try {
            JSONObject job = new JSONObject(json);
            mp.setWeatherIcon(job.getString("Vrijeme"));
            mp.setWeatherValue(job.getString("VrijemeOpis"));
            mp.setTemperatureValue(Float.valueOf(job.getString("Temperatura")));
            mp.setTemperatureMax(Float.valueOf(job.getString("TemperaturaMax")));
            mp.setTemperatureMin(Float.valueOf(job.getString("TemperaturaMin")));
            mp.setHumidityValue(Float.valueOf(job.getString("Vlažnost")));
            mp.setPressureValue(Float.valueOf(job.getString("Tlak")));

            try {
                Date date;
                date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(job.getString("Preuzeto"));
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(date);
                try {
                    XMLGregorianCalendar xmlGrogerianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
                    mp.setLastUpdate(xmlGrogerianCalendar);
                   
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(MeteoRestKlijent.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(date);
                
            } catch (ParseException ex) {
                Logger.getLogger(MeteoRestKlijent.class.getName()).log(Level.SEVERE, null, ex);
            }

           
            
            return mp;

        } catch (JSONException ex) {
            System.out.println("JSON EXCEPTION");
        }

        return mp;
    }

    /**
     * Metoda delete
     * @throws ClientErrorException
     */
    public void delete() throws ClientErrorException {
        webTarget.request().delete();
    }

    /**
     *Metoda close klijenta
     */
    public void close() {
        client.close();
    }

}
