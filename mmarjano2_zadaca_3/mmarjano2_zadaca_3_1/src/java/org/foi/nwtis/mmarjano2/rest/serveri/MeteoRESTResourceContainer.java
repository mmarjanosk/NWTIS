/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.rest.serveri;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.foi.nwtis.mmarjano2.rest.klijenti.GMKlijent;
import org.foi.nwtis.mmarjano2.web.SqlRunner;
import org.foi.nwtis.mmarjano2.web.podaci.Lokacija;
import org.foi.nwtis.mmarjano2.web.podaci.Uredjaj;
import org.foi.nwtis.mmarjano2.ws.serveri.GeoMeteoWS;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author grupa_2
 */
@Path("/meteoREST")
public class MeteoRESTResourceContainer {

    private String id;

    @Context
    private UriInfo context;

    @javax.ws.rs.core.Context
    ServletContext scontext;

    /**
     * Konstruktor MeteoRESTResourceContainer-aq
     */
    public MeteoRESTResourceContainer() {
        ;
    }

    /**
     * Metoda za dohvaćanje IOT uređaja
     *
     * @return JSON string
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {

        System.out.println(scontext.getAttribute("APIKey"));

        SqlRunner se = new SqlRunner(scontext);

        String sql = "Select * from uredaji";
        List<Uredjaj> uredaji = new ArrayList<>();

        ResultSet resultSet = se.izvrsiSQL(sql);

        System.out.println("nakon sql");

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

        System.out.println(uredaji.size());

        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Uredjaj uredjaj : uredaji) {

            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("uid", uredjaj.getId());
            job.add("naziv", uredjaj.getNaziv());

            System.out.println(job);

            jab.add(job);
        }

        return jab.build().toString();
    }

    /**
     * Metoda za unošenje novog IOT uređanja
     *
     * @param content JSON sadržaja naziv i adresa
     * @return  HTTP response sa sadržajem resursa
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {

        try {
            System.out.println(content);
            JSONObject jo = new JSONObject(content);
            String adresa = jo.getString("uid");
            String naziv = jo.getString("naziv");

            SqlRunner se = new SqlRunner(scontext);
            GMKlijent gmk = new GMKlijent();
            Lokacija l = gmk.getGeoLocation(adresa);
            String sql;
            String latitude = l.getLatitude();
            String longitude = l.getLongitude();
            String id = (String) scontext.getAttribute("MaxID");
            int MXid = Integer.valueOf(id);
            MXid++;
             scontext.setAttribute("MaxID", String.valueOf(MXid));
            sql = "insert into uredaji (id,naziv,latitude,longitude) values(" + MXid + ",\'" + naziv + "\'," + latitude + "," + longitude + ")";
            System.out.println(sql);
            se.doSQL(sql);

        } catch (JSONException ex) {
            Logger.getLogger(MeteoRESTResourceContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator metoda za {id}
     * @param id
     * @return MeteoRESTResource instanca
     */
    @Path("{id}")
    public MeteoRESTResource getMeteoRESTResource(@PathParam("id") String id) {
        return MeteoRESTResource.getInstance(id);
    }

}
