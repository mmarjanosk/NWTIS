/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.dretve;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import javax.servlet.ServletContext;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;

/**
 *
 * @author Matija
 */
public class ObradaPoruka extends Thread {

    private boolean prekid_obrade = false;
    private ServletContext sc = null;
    Connection con;
   

    public ObradaPoruka(ServletContext kontekst) {
        this.sc = kontekst;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        int trajanjeObrade = 0;
        int brojPoruka = 0;
        int brojDodanihIOT = 0;
        int brojMjerenihTemp = 0;
        int brojIzvrsenihEVENT = 0;
        int brojPogresaka = 0;

        String sintaksaADD = "ADD IoT (d{1-6} \".{1-30}\") GPS: (-?d{1-3}.d{6}),(-?d{1-3}.d{6})";
        String sintaksaTEMP = "TEMP IoT d{1-6} T: yyyy.MM.dd hh:mm:ss C: -?d{1-2}.d";
        String sintaksaEVENT = "EVENT IoT d{1-6} T: yyyy.MM.dd hh:mm:ss F: d{1-2}";
        
        Konfiguracija konf = (Konfiguracija) sc.getAttribute("Mail_Konfig");
        String server = konf.dajPostavku("mail.server");
        String port = konf.dajPostavku("mail.port");
        String korisnik = konf.dajPostavku("mail.usernameThread");
        String lozinka = konf.dajPostavku("mail.passwordThread");
        int trajanjeCiklusa = Integer.parseInt(konf.dajPostavku("mail.timeSecThread"));
        //TODO i za ostale parametre

        //TODO odredi trajanje obrade
        int redniBrojCiklusa = 0;

        while (true) {
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", server);
            Session session = Session.getInstance(properties, null);

            Store store;
            try {
                store = session.getStore("imap");
                store.connect(server, korisnik, lozinka);

                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_ONLY);

                Message[] messages = folder.getMessages();

                for (int i = 0; i < messages.length; ++i) {

                    Message message = messages[i];
                    Object sender = message.getFrom();
                    Object head =message.getSubject();
                    Object content = message.getContent();

                    brojPoruka++;
                    String vrstaPoruke = message.getContentType();
                    if (vrstaPoruke.equals("text/plain")) {
                        
                        Statement stmt =con.createStatement();
                        Pattern pattern = Pattern.compile(sintaksaADD);
                        Matcher m = pattern.matcher(content.toString());
                        boolean status = m.matches();
                        if(status){
                            //TODO za ADD Za poruku koja ima ispravnu sintaksu slijedi provođenja postupka: 
                            //ako se radi o komandi ADD tada treba dodati zapis u tablicu UREDAJI u bazi podataka, s time da ako već postoji, onda je pogreška.
                            
                            brojDodanihIOT++;
                            status=false;
                        }
                        
                        pattern = Pattern.compile(sintaksaTEMP);
                        m = pattern.matcher(content.toString());
                        status = m.matches();
                        if(status){
                            //TODO za TEMP
                            
                            brojMjerenihTemp++;
                            status=false;
                        }
                        
                        pattern = Pattern.compile(sintaksaEVENT);
                        m = pattern.matcher(content.toString());
                        status = m.matches();
                        if(status){
                            //TODO za EVENT
                            brojIzvrsenihEVENT++;
                            status=false;
                        }
                        
                        
                    } else {
                        brojPogresaka++;
                    }

                }
                redniBrojCiklusa++;
                
                System.out.println("Obrada prouka u cilkusu: " + redniBrojCiklusa);
                sleep(trajanjeCiklusa * 1000 - trajanjeObrade);

            } catch (NoSuchProviderException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSc(ServletContext sc) {
        this.sc = sc;
    }

}
