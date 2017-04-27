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
    static int str = 1;
    private Folder folder;
    static Store store;
    private boolean min = true;
    private boolean max = false;
    private boolean seek = true;
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
        try {
            store = session.getStore("imap");
            store.connect(server, korisnik, lozinka);
//            folder = store.getFolder(odabranaMapa);
//            folder.open(Folder.READ_WRITE);

            //messages = folder.getMessages();
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
        folder = store.getFolder(odabranaMapa);
        folder.open(Folder.READ_WRITE);
        ukupnoPorukaMapa = folder.getMessageCount();
        
        if (pozicijaDoPoruke >= folder.getMessageCount()) {
            pozicijaDoPoruke = folder.getMessageCount();
            setMax(true);
        } else {
            setMax(false);
        }
        if (str == 1) {
            setMin(true);
        } else {
            setMin(false);
        }

        if (folder.getMessageCount() > 0) {
            messages = folder.getMessages(pozicijaOdPoruke + 1, pozicijaDoPoruke);
        }
        int br = brojPrikazanihPoruka * (str - 1);
        if (messages != null) {

            System.out.println(pozicijaOdPoruke);
            System.out.println(pozicijaDoPoruke);
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Date sdat = message.getSentDate();
                Date rdat = message.getReceivedDate();
                br++;
                Address[] sa = message.getFrom();
                String salje = sa[0].toString();
                salje = salje.split(":;")[0];
                String sadrzaj = message.getContent().toString();
                String predmet = message.getSubject();
                String vrsta = message.getContentType();
                if(!isSeek()){
                    if(sadrzaj.contains(traziPoruke)){
                         poruke.add(new Poruka(Integer.toString(br), rdat, sdat, salje, predmet, sadrzaj, vrsta));
                    }
                } else {
                     poruke.add(new Poruka(Integer.toString(br), rdat, sdat, salje, predmet, sadrzaj, vrsta));
                }
               
            }
        }
        folder.close(true);
    }

    public String promjenaMape() throws MessagingException, NoSuchProviderException, IOException {

        this.pozicijaOdPoruke = 0;
        this.pozicijaDoPoruke = brojPrikazanihPoruka;
        this.str = 1;
        this.preuzmiPoruke(odabranaMapa);
        return "PromjenaMape";
    }

    public String traziPoruke() throws MessagingException, NoSuchProviderException, IOException {
        this.preuzmiPoruke(odabranaMapa);
        return "FiltrirajPoruke";
    }

    public String prethodnePoruke() throws MessagingException, NoSuchProviderException, IOException {
        if (!min) {
            this.str--;
            this.pozicijaOdPoruke = this.brojPrikazanihPoruka * (this.str - 1);
            this.pozicijaDoPoruke = this.brojPrikazanihPoruka * this.str;
            this.preuzmiPoruke(odabranaMapa);
        }
        return "PrethodnePoruke";
    }

    public String sljedecePoruke() throws MessagingException, NoSuchProviderException, IOException {
        if (max) {

        } else {
            this.str++;
            this.pozicijaOdPoruke = this.brojPrikazanihPoruka * (this.str - 1);
            this.pozicijaDoPoruke = this.brojPrikazanihPoruka * this.str;
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

    public void setTraziPoruke(String traziPoruke) throws MessagingException, NoSuchProviderException, IOException{
        this.traziPoruke = traziPoruke;
        preuzmiPoruke(odabranaMapa);
        seek=false;
    }

    public ArrayList<Izbornik> getMape() {
        return mape;
    }

    public ArrayList<Poruka> getPoruke() {
        return poruke;
    }

    public boolean isMin() {
        return min;
    }

    public void setMin(boolean min) {
        this.min = min;
    }

    public boolean isMax() {
        return max;
    }

    public void setMax(boolean max) {
        this.max = max;
    }

    public boolean isSeek() {
        return seek;
    }

    public void setSeek(boolean seek) throws MessagingException, NoSuchProviderException, IOException {
        this.seek = seek;
        preuzmiPoruke(odabranaMapa);
    }

    
    
    
    
    
}
