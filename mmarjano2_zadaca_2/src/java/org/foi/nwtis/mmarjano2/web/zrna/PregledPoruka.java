/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.zrna;

import com.mysql.jdbc.Messages;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.mmarjano2.web.kontrole.Izbornik;
import org.foi.nwtis.mmarjano2.web.kontrole.Poruka;

/**
 *
 * @author grupa_2
 */
@Named(value = "pregledPoruka")
@RequestScoped
public class PregledPoruka {

    private ArrayList<Izbornik> mape = new ArrayList<>();
    private static String odabranaMapa = "INBOX";
    private ArrayList<Poruka> poruke = new ArrayList<>();
    private int ukupnoPorukaMapa = 0;
    private Message[] messages;

    private String traziPoruke;

    HttpServletRequest context = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    ServletContext sc = context.getServletContext();
    Konfiguracija konf = (Konfiguracija) sc.getAttribute("Mail_Konfig");
    String server = konf.dajPostavku("mail.server");
    String port = konf.dajPostavku("mail.port");
    String korisnik = konf.dajPostavku("mail.usernameThread");
    String lozinka = konf.dajPostavku("mail.passwordThread");
    int brojPrikazanihPoruka = Integer.parseInt(konf.dajPostavku("mail.numMessages"));
    int pozicijaOdPoruke = 0;
    int pozicijaDoPoruke = brojPrikazanihPoruka;
    java.util.Properties properties = System.getProperties();

    /**
     * Creates a new instance of PregledPoruka
     */
    public PregledPoruka() throws MessagingException, IOException {
        properties.put("mail.smtp.host", server);
        Session session = Session.getInstance(properties, null);
        Store store;
        try {
            store = session.getStore("imap");
            store.connect(server, korisnik, lozinka);
            Folder folder = store.getFolder(odabranaMapa);
            folder.open(Folder.READ_WRITE);
            messages = folder.getMessages();
            preuzmiMape(store);
            preuzmiPoruke(odabranaMapa);

        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void preuzmiMape(Store store) throws MessagingException {
        //TODO promjeni sa stvarnim preuzimanjem mapa

        Folder[] folders = store.getDefaultFolder().list();
        for (int i = 0; i < folders.length; ++i) {
            Folder folder = folders[i];

            mape.add(new Izbornik(folder.getName(), folder.getFullName()));
        }

    }

    void preuzmiPoruke(String mapa) throws NoSuchProviderException, MessagingException, IOException {
        poruke.clear();
       
        if (pozicijaDoPoruke > messages.length) {
            pozicijaDoPoruke = messages.length;
        }

        System.out.println(pozicijaOdPoruke);
        System.out.println(pozicijaDoPoruke);
        for (int i = pozicijaOdPoruke; i < pozicijaDoPoruke; i++) {
            Message message = messages[i];
            Date sdat = message.getSentDate();
            Date rdat = message.getReceivedDate();

            Address[] sa = message.getFrom();
            String salje = sa[0].toString();
            salje = salje.split(":;")[0];
            String sadrzaj = message.getContent().toString();
            String predmet = message.getSubject();
            String vrsta = message.getContentType();
            System.out.println("Poruka " + i);
             

            poruke.add(new Poruka(Integer.toString(i), rdat, sdat, salje, predmet, sadrzaj, vrsta));

        }
        
        ukupnoPorukaMapa = messages.length;
    }

    public String promjenaMape() throws MessagingException, NoSuchProviderException, IOException {
        this.preuzmiPoruke(odabranaMapa);
        this.pozicijaOdPoruke = 0;
        this.pozicijaDoPoruke = brojPrikazanihPoruka;
        return "PromjenaMape";
    }

    public String traziPoruke() throws MessagingException, NoSuchProviderException, IOException {
        this.preuzmiPoruke(odabranaMapa);
        return "FiltrirajPoruke";
    }

    public String prethodnePoruke() throws MessagingException, NoSuchProviderException, IOException {
        if (pozicijaOdPoruke != 0) {
            this.pozicijaDoPoruke = this.pozicijaDoPoruke - this.brojPrikazanihPoruka;
            this.pozicijaOdPoruke = this.pozicijaOdPoruke - this.brojPrikazanihPoruka;
            this.preuzmiPoruke(odabranaMapa);
        }
        return "PrethodnePoruke";
    }

    public String sljedecePoruke() throws MessagingException, NoSuchProviderException, IOException {
        if (pozicijaDoPoruke == ukupnoPorukaMapa) {

        } else {

            this.pozicijaOdPoruke = this.pozicijaOdPoruke + this.brojPrikazanihPoruka;
            this.pozicijaDoPoruke = this.pozicijaDoPoruke + this.brojPrikazanihPoruka;
            this.preuzmiPoruke(odabranaMapa);
        }
        return "SljedecePoruke";
    }

    public String promjenaJezika() {
        return "PromjenaJezika";
    }

    public String saljiPoruku() {
        return "SaljiPoruku";
    }

    public String getOdabranaMapa() {
        return odabranaMapa;
    }

    public void setOdabranaMapa(String odabranaMapa) {
        this.odabranaMapa = odabranaMapa;
    }

    public int getUkupupnoPorukaMapa() {
        return ukupnoPorukaMapa;
    }

    public void setUkupupnoPorukaMapa(int ukupupnoPorukaMapa) {
        this.ukupnoPorukaMapa = ukupupnoPorukaMapa;
    }

    public String getTraziPoruke() {
        return traziPoruke;
    }

    public void setTraziPoruke(String traziPoruke) {
        this.traziPoruke = traziPoruke;
    }

    public ArrayList<Izbornik> getMape() {
        return mape;
    }

    public ArrayList<Poruka> getPoruke() {
        return poruke;
    }

}
