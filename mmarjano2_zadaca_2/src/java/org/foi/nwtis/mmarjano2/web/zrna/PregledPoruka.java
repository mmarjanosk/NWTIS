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
 * Klasa za pregleda poruka
 *
 * @author Matija
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
    private String traziPoruke = "";

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
     * Konstruktor klase Pregled Poruka
     *
     * @throws MessagingException
     * @throws IOException
     */
    public PregledPoruka() throws MessagingException, IOException {
        properties.put("mail.smtp.host", server);
        Session session = Session.getInstance(properties, null);
        try {
            store = session.getStore("imap");
            store.connect(server, korisnik, lozinka);

            preuzmiMape(store);
            preuzmiPoruke(odabranaMapa);

        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metoda za preuzimanje foldera iz store-a
     *
     * @param store - Store store iz kojeg se preuzimaju poruke
     * @throws MessagingException
     */
    void preuzmiMape(Store store) throws MessagingException {
        //TODO promjeni sa stvarnim preuzimanjem mapa

        Folder[] folders = store.getDefaultFolder().list();
        for (int i = 0; i < folders.length; ++i) {
            Folder folder = folders[i];
            int brojpor = folder.getMessageCount();

            mape.add(new Izbornik(folder.getName(), folder.getFullName(), brojpor));
        }

    }

    /**
     * Metoda za prikazivanje poruka iz određene mape na određenoj stranici
     *
     * @param mapa - String mapa iz koje se prikazuju poruke
     * @throws NoSuchProviderException
     * @throws MessagingException
     * @throws IOException
     */
    void preuzmiPoruke(String mapa) throws NoSuchProviderException, MessagingException, IOException {

        poruke.clear();
        folder = store.getFolder(odabranaMapa);
        folder.open(Folder.READ_WRITE);
        ukupnoPorukaMapa = folder.getMessageCount();

        pozicijaOdPoruke = ukupnoPorukaMapa - (str * brojPrikazanihPoruka);
        pozicijaDoPoruke = ukupnoPorukaMapa - ((str - 1) * brojPrikazanihPoruka);

        if (pozicijaDoPoruke == ukupnoPorukaMapa) {

            setMin(true);
        } else {
            setMin(false);
        }
        if (pozicijaOdPoruke <= 0) {
            pozicijaOdPoruke = 1;
            if (pozicijaDoPoruke == ukupnoPorukaMapa) {
                pozicijaDoPoruke = ukupnoPorukaMapa;
            } else {
                pozicijaDoPoruke = ukupnoPorukaMapa % brojPrikazanihPoruka + 1;
            }
            setMax(true);
        } else {
            setMax(false);
        }

        if (folder.getMessageCount() > 0) {

            messages = folder.getMessages(pozicijaOdPoruke, pozicijaDoPoruke - 1);
        }
        int br = brojPrikazanihPoruka * (str - 1);
        if (messages != null) {

          
            for (int i = messages.length - 1; i >= 0; i--) {
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
                if (!isSeek()) {
                    if (sadrzaj.contains(traziPoruke)) {
                        poruke.add(new Poruka(Integer.toString(br), rdat, sdat, salje, predmet, sadrzaj, vrsta));
                    }
                } else {
                    poruke.add(new Poruka(Integer.toString(br), rdat, sdat, salje, predmet, sadrzaj, vrsta));

                }

            }
        }

    }

    /**
     * Metoda za promjenu foldera iz kojeg se čitaju poruke
     *
     * @return String PromjenaMape
     * @throws MessagingException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    public String promjenaMape() throws MessagingException, NoSuchProviderException, IOException {

        seek = true;
        this.pozicijaOdPoruke = 0;
        this.pozicijaDoPoruke = brojPrikazanihPoruka;
        this.str = 1;
        this.preuzmiPoruke(odabranaMapa);
        return "PromjenaMape";
    }

    /**
     * Metoda za pretragu sadržaja poruka s klljučnom riječi
     *
     * @return String Filtriraj poruke
     * @throws MessagingException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    public String traziPoruke() throws MessagingException, NoSuchProviderException, IOException {
        this.preuzmiPoruke(odabranaMapa);
        return "FiltrirajPoruke";
    }

    /**
     * Metoda za vraćanje prethodne stranice ako postoji
     *
     * @return String PrethodnePoruke
     * @throws MessagingException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    public String prethodnePoruke() throws MessagingException, NoSuchProviderException, IOException {
        if (!min) {
            this.str--;

            this.preuzmiPoruke(odabranaMapa);
        }
        return "PrethodnePoruke";
    }

    /**
     * Metoda za navigaciju na slijedeću stranicu
     *
     * @return String SljedećePoruke
     * @throws MessagingException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    public String sljedecePoruke() throws MessagingException, NoSuchProviderException, IOException {

        if (max) {

        } else {
            this.str++;

            this.preuzmiPoruke(odabranaMapa);
        }
        return "SljedecePoruke";
    }

    /**
     * Metoda za navigaciju na str za promjenu jezika
     *
     * @return String PromjenaJezika
     */
    public String promjenaJezika() {
        return "PromjenaJezika";
    }

    /**
     * Metoda za navigaciju na str za slanje poruke
     *
     * @return String SaljiPoruku
     */
    public String saljiPoruku() {
        return "SaljiPoruku";
    }

    /**
     * Getter metoda za odabranu maput
     *
     * @return String odabrana mapa
     */
    public String getOdabranaMapa() {
        return odabranaMapa;
    }

    /**
     * Setter Metoda za odrabranu mapu
     *
     * @param odabranaMapa String odabrana mapa
     */
    public void setOdabranaMapa(String odabranaMapa) {
        this.odabranaMapa = odabranaMapa;
    }

    /**
     * Getter metoda za ukupam broj poruka u mapi
     *
     * @return int ukupno poruka u mapi
     */
    public int getUkupupnoPorukaMapa() {
        return ukupnoPorukaMapa;
    }

    /**
     * Setter metoda za ukupan broj poruka u mapi
     *
     * @param ukupupnoPorukaMapa
     */
    public void setUkupupnoPorukaMapa(int ukupupnoPorukaMapa) {
        this.ukupnoPorukaMapa = ukupupnoPorukaMapa;
    }

    /**
     * Getter za traženje poruka
     *
     * @return String traži poruke
     */
    public String getTraziPoruke() {
        return traziPoruke;
    }

    /**
     * Setter za pretragu poruka
     *
     * @param traziPoruke - String varijabla s kojom se uspoređuju sadržaji
     * poruka
     * @throws MessagingException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    public void setTraziPoruke(String traziPoruke) throws MessagingException, NoSuchProviderException, IOException {
        this.traziPoruke = traziPoruke;

        preuzmiPoruke(odabranaMapa);
        seek = false;
    }

    /**
     * Getter metoda za listu mapa
     *
     * @return ArrayList mape
     */
    public ArrayList<Izbornik> getMape() {
        return mape;
    }

    /**
     * Getter metoda za poruke
     *
     * @return ArrayList poruka
     */
    public ArrayList<Poruka> getPoruke() {
        return poruke;
    }

    /**
     * Metoda koja provjerava je li u pitanju prva stranica
     *
     * @return true ako je prva stranica, false ako nije
     */
    public boolean isMin() {
        return min;
    }

    /**
     * Setter metoda za provjeru prve stranice
     *
     * @param min Boolean - true ako je prva, false ako niej
     */
    public void setMin(boolean min) {
        this.min = min;
    }

    /**
     * Metoda koja provjerava je li trenutna stranica zadnja
     *
     * @return true ako je zadnja, false ako nije zadnja stranica
     */
    public boolean isMax() {
        return max;
    }

    /**
     * Metoda koja postavlja vrijednost zadnje stranice
     *
     * @param max Boolean, true ako je zadnja, false ako nije
     */
    public void setMax(boolean max) {
        this.max = max;
    }

    /**
     * Metoda koja postavlja flag seek, koji daje uvid je li pretraga uključena
     *
     * @return boolean seek - true ako je uključena pretraga, false ako nije
     */
    public boolean isSeek() {
        return seek;
    }

    /**
     * Metoda za postavljanja flaga za pretragu
     *
     * @param seek - boolean true ako je pretraga uključena, false ako nije
     * @throws MessagingException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    public void setSeek(boolean seek) throws MessagingException, NoSuchProviderException, IOException {
        this.seek = seek;
        preuzmiPoruke(odabranaMapa);
    }

}
