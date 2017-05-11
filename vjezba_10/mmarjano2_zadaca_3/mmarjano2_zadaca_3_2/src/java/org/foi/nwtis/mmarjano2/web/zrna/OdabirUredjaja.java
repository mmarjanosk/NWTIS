/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.zrna;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.xml.datatype.XMLGregorianCalendar;

import org.foi.nwtis.mmarjano2.web.klijent.MeteoPodaci;
import org.foi.nwtis.mmarjano2.web.klijent.Uredjaj;
import org.foi.nwtis.mmarjano2.web.klijenti.MeteoRestC;
import org.foi.nwtis.mmarjano2.web.klijenti.MeteoRestKlijent;

import org.foi.nwtis.mmarjano2.web.klijenti.MeteoWSKlijent;

/**
 * JSF managed bean klasa
 * @author Matija Marjanović
 */
@Named(value = "odabirUredjaja")
@RequestScoped
public class OdabirUredjaja {

    private List<Uredjaj> uredjaji;
    private List<Uredjaj> uredjajiREST;

    private String id;
    private String naziv = null;
    private String adresa = null;
    private String from;
    private String to;
    private List<MeteoPodaci> mp;
    private List<MeteoPodaci> zadnji;
    private List<Float> mM;
    private List<Float> vlaga;
    private List<Float> tlak;
    private List<String> Odabrani;
    private Float tempmax;
    private Float tempmin;
    private Float tlakmin;
    private Float tlakmax;
    private Float vlagamax;
    private Float vlagamin;
    private String ime;
    private String error;
    private boolean visible = false;
    private boolean visibleREST = false;

    /**
     * Getter za tempmax
     * @return Float tempmax
     */
    public Float getTempmax() {
        return tempmax;
    }
/**
     * Getter za tempmin
     * @return Float tempmib
     */
    public Float getTempmin() {
        return tempmin;
    }

    /**
     * Konstruktor JSF Managed Beana
     */
    public OdabirUredjaja() {
    }

    /**
     * Getter za listu uredaja
     * @return List Uredjaj
     */
    public List<Uredjaj> getUredjaji() {
        uredjaji = MeteoWSKlijent.dajSveuredaje();
        return uredjaji;

    }

    /**
     * Setter  za Uredjaji
     * @param uredjaji List Uredjaj
     */
    public void setUredjaji(List<Uredjaj> uredjaji) {
        this.uredjaji = uredjaji;

    }

    /**
     * Getter za listu odabranih uredaja
     * @return List String
     */
    public List<String> getOdabrani() {
        return Odabrani;
    }

    /**
     * Setter za listu odabranih uredaja
     * @param Odabrani List String
     */
    public void setOdabrani(List<String> Odabrani) {
        this.Odabrani = Odabrani;
    }

    /**
     * getter za Id uredaja
     * @return String id uredaja
     */
    public String getId() {
        return id;
    }

    /**
     * Setter za ID uredaja
     * @param id String id uredaja
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter za naziv uredaja
     * @return String naziv
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Setter za naziv uredaja  
     * @param naziv String naziv 
     */
    public void setNaziv(String naziv) {
        Charset.forName("UTF-8").encode(naziv);
        this.naziv = naziv;
    }

    /**
     * Getter za adresu uredaja 
     * @return String adresa
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * Setter za adresu 
     * @param adresa String adresa
     */
    public void setAdresa(String adresa) {
        Charset.forName("UTF-8").encode(adresa);
        this.adresa = adresa;
    }

    /**
     * Getter za početno vrijeme
     * @return String vrijeme od
     */
    public String getFrom() {
        return from;
    }

    /**
     * Setter za početno vrijeme 
     * @param from String from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Getter za završno vrijeme 
     * @return String završno vrijeme
     */
    public String getTo() {
        return to;
    }

    /**
     * Setter za završno vrijeme 
     * @param to String završno vrijeme
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Getter za min max temperaturu
     * @return List Float
     */
    public List<Float> getmM() {
        return mM;
    }

    /**
     * Getter za min max vlagu
     * @return List Float
     */
    public List<Float> getVlaga() {
        return vlaga;
    }

    /**
     * Getter za min max tlak
     * @return List float
     */
    public List<Float> getTlak() {
        return tlak;
    }

    /**
     * Getter za dohvaćanje liste važećih meteo podataka
     * @return List MeteoPodaci
     */
    public List<MeteoPodaci> getZadnji() {
        return zadnji;
    }

    /**
     * Getter za listu REST uredaja
     * @return List Uredaj
     */
    public List<Uredjaj> getUredjajiREST() {
        return uredjajiREST;
    }

    /**
     * Metoda za dohvaćanje liste meteo podataka
     * @return List MeteoPodaci
     */
    public List<MeteoPodaci> getMp() {
        return mp;
    }

    /**
     * Getter za min tlak
     * @return FLoat min tlak
     */
    public Float getTlakmin() {
        return tlakmin;
    }

    /**
     * getter za max tlak
     * @return Float max tlak
     */
    public Float getTlakmax() {
        return tlakmax;
    }

    /**
     * getter za max Vlagu
     * @return Float max vlaga
     */
    public Float getVlagamax() {
        return vlagamax;
    }

    /**
     * Getter za min vlagu
     * @return Float min vlaga
     */
    public Float getVlagamin() {
        return vlagamin;
    }

    /**
     * Getter za ime uredaja
     * @return String ime uredaja
     */
    public String getIme() {
        return ime;
    }

    /**
     * Getter za error message
     * @return String error message
     */
    public String getError() {
        return error;
    }

    /**
     * Getter za Soap flag
     * @return boolean 
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Getter za REST flag
     * @return boolean
     */
    public boolean isVisibleREST() {
        return visibleREST;
    }

    /**
     * Metoda za REST upisivanje novog IOT uredaja
     */
    public void upisiREST() {
        System.out.println(adresa);
        System.out.println(naziv);
        MeteoRestC mrc = new MeteoRestC();
        mrc.postJson(adresa, naziv);

    }

    /**
     * Metoda za SOAP upisivanje novog IOT uredaja
     */
    public void upisiSOAP() {
        MeteoWSKlijent.dodajUredaj(naziv, adresa);

    }

    /**
     * Metoda za preuzimanje REST podataka
     */
    public void preuzmiREST() {

        if (Odabrani.size() > 1) {

            MeteoRestC mrc = new MeteoRestC();
            uredjajiREST = mrc.getJson();
            visible = false;

            String RESTid = null;
            zadnji = new ArrayList<>();

            for (String oid : Odabrani) {
                RESTid = oid;
                System.out.println(RESTid);

                try {
                    MeteoRestKlijent mr = new MeteoRestKlijent(RESTid);

                    zadnji.add(mr.getJson2());
                    System.out.println("Veličin zadnjeg je :" + zadnji.size());
                } catch (Exception e) {
                    System.out.println("nema");
                }

                System.out.println();
                visibleREST = true;
            }

        }
    }

    /**
     * Metoda za preuzimanje SOAP podataka
     */
    public void preuzmiSOAP() {
        if (Odabrani.size() == 1) {
            long od = 0;

            long dos = 0;
            int SOAPid = 0;

            try {
                od = Timestamp.valueOf(from).getTime();
                dos = Timestamp.valueOf(to).getTime();
            } catch (Exception e) {
                error = "Pogrešan datum";
            }

            try {
                id = Odabrani.get(0);
                SOAPid = Integer.valueOf(id);
            } catch (Exception e) {
                error = "Nije odabran uređaj";
            }

            try {
                mp = MeteoWSKlijent.dajSveMeteoPodatkeZaUredjaj(SOAPid, od, dos);
                mM = MeteoWSKlijent.minMaxTemp(SOAPid, od, dos);
                vlaga = MeteoWSKlijent.minMaxVlaga(SOAPid, od, dos);
                tlak = MeteoWSKlijent.minMaxTlak(SOAPid, od, dos);
                System.out.println("  ID: " + SOAPid + "  Naziv: " + ime);
                zadnji = new ArrayList<>();
                zadnji.add(MeteoWSKlijent.dajPosljednjePodatke(SOAPid));

                tempmax = mM.get(0);
                tempmin = mM.get(1);

                tlakmax = tlak.get(0);
                tlakmin = tlak.get(1);

                vlagamax = vlaga.get(0);
                vlagamin = vlaga.get(1);

                for (Uredjaj uredjajs : uredjaji) {
                    if (uredjajs.getId() == SOAPid) {
                        ime = uredjajs.getNaziv();
                    }
                }
                visible = true;
                visibleREST = false;
            } catch (Exception e) {
                error = "Pogreška pri dohvaćanju podataka";
            }

        }
    }

    /**
     * Pomoćna metoda za konvertiranje datuma
     * @param calendar XMLGregorianCalendar 
     * @return Date
     */
    public Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

}
