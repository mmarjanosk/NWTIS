/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.dretve;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.ServletContext;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.mmarjano2.web.SlanjeMaila;

/**
 *
 * @author grupa_2
 */
public class ObradaPoruka extends Thread {

    private boolean prekid_obrade = false;
    private ServletContext sc = null;
    public Store store;

    @Override
    public void interrupt() {
        prekid_obrade = true;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        long vrijemePocetka;
        long vrijemeZavrsetka;

        boolean isCreated;

        String sintaksaADD = "^ADD IoT (\\d{1,6}) \\\"(.{1,30})\\\" ?GPS: (-?\\d{1,3}\\.\\d{6}),(-?\\d{1,3}\\.\\d{6});$";
        String sintaksaTEMP = "^TEMP IoT ([0-9]{1,6}) T: ((\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])) ((?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d))) C: (\\d*\\.?\\d);$";
        String sintaksaEVENT = "^EVENT IoT ([0-9]{1,6}) T: ((\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])) (?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)) F: (\\d*);$";
        String sintaksaTEXT = "TEXT\\/PLAIN; ([^\\s]+)";
        Konfiguracija konf = (Konfiguracija) sc.getAttribute("Mail_Konfig");
        String server = konf.dajPostavku("mail.server");
        String port = konf.dajPostavku("mail.port");
        String korisnik = konf.dajPostavku("mail.usernameThread");
        String folder_valjano = konf.dajPostavku("mail.folderNWTiS");
        String folder_nevaljano = konf.dajPostavku("mail.folderOther");
        String lozinka = konf.dajPostavku("mail.passwordThread");
        int trajanjeCiklusa = Integer.parseInt(konf.dajPostavku("mail.timeSecThread"));
        String statadr = konf.dajPostavku("mail.usernameStatistics");
        String statsubject = konf.dajPostavku("mail.subjectStatistics");
        String nwtisSubject= konf.dajPostavku("mail.subject");
        BP_Konfiguracija BPkonf = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");

        int redniBrojCiklusa = 0;
        SlanjeMaila slanjeMaila = new SlanjeMaila();

        while (!prekid_obrade) {
            // Start the session

            vrijemePocetka = System.currentTimeMillis();
            int trajanjeObrade = 0;
            int brojPoruka = 0;
            int brojDodanihIOT = 0;
            int brojMjerenihTEMP = 0;
            int brojIzvrsenihEVENT = 0;
            int brojPogresaka = 0;

            // Connect to the store
            try {

                java.util.Properties properties = System.getProperties();
                properties.put("mail.smtp.host", server);
                Session session = Session.getInstance(properties, null);

                store = session.getStore("imap");
                store.connect(server, Integer.valueOf(port), korisnik, lozinka);

                Folder NWTIS = store.getFolder(folder_valjano);
                Folder NWTISO = store.getFolder(folder_nevaljano);

                if (!NWTIS.exists()) {
                    try {
                        Folder parent = store.getDefaultFolder();
                        NWTIS = parent.getFolder(folder_valjano);
                        isCreated = NWTIS.create(Folder.HOLDS_MESSAGES);
                        System.out.println("created: " + isCreated);

                    } catch (Exception e) {
                        System.out.println("Error creating folder: " + e.getMessage());
                        e.printStackTrace();
                        isCreated = false;
                    }
                }

                if (!NWTISO.exists()) {
                    try {
                        Folder parent = store.getDefaultFolder();
                        NWTISO = parent.getFolder(folder_valjano);
                        isCreated = NWTIS.create(Folder.HOLDS_MESSAGES);
                        System.out.println("created: " + isCreated);

                    } catch (Exception e) {
                        System.out.println("Error creating folder: " + e.getMessage());
                        e.printStackTrace();
                        isCreated = false;
                    }
                }
                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_WRITE);
//
//                NWTIS.open(Folder.READ_WRITE);    
//                Message[] messages1 = NWTIS.getMessages();
//                for (int i = 1; i < messages1.length; ++i) {
//                    Message message=messages1[i];
//                    messages1[i].setFlag(Flags.Flag.DELETED, true);
//                    NWTIS.expunge();
//                    System.out.println(NWTIS.getMessageCount());
//              
//                }
//                NWTIS.expunge();
//                NWTIS.getMessageCount();

////                NWTIS.close(true);
////                System.out.println("Prazan1");
//                NWTISO.open(Folder.READ_WRITE);
//                
//                Message[] messages2 = NWTISO.getMessages();
//                for (int i = 1; i < messages2.length; ++i) {
//                    Message message = messages2[i];
//                    messages2[i].setFlag(Flags.Flag.DELETED, true);
//                    NWTISO.expunge();
//                    System.out.println(NWTISO.getMessageCount());                 
//                }
//                NWTISO.expunge();
//                NWTISO.close(true);
//                System.out.println("Prazan2");
                //pripremazabazu
                String connURL = BPkonf.getServerDatabase() + BPkonf.getUserDatabase();
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;
                String ua = BPkonf.getUserUsername();
                String ap = BPkonf.getUserPassword();

                try {
                    Class.forName(BPkonf.getDriverDatabase());
                } catch (ClassNotFoundException ex) {
                    System.out.println("Nemoguće čitati driver" + ex.getMessage());
                    return;
                }

                Message[] messages = folder.getMessages();
                for (int i = 0; i < messages.length; ++i) {
                    Message message = messages[i];

                    brojPoruka++;

                    String vrstaPoruke = message.getContentType();
                    Pattern patternt = Pattern.compile(sintaksaTEXT);
                    Matcher mt = patternt.matcher(vrstaPoruke);
                    boolean textos = mt.matches();
                    System.out.println(nwtisSubject);
                    System.out.println(message.getSubject());
                  
                    if (textos && message.getSubject().equals(nwtisSubject)) {
                        String content = (String) message.getContent();

                        content = content.trim();
                        content = content.split(";")[0] + ";";
                        boolean flag = false;
                        Pattern pattern = Pattern.compile(sintaksaADD);
                        Matcher m = pattern.matcher(content);
                        boolean status = m.matches();
                        if (status) {
                            System.out.println("SINTAKSA:ADD");
                            System.out.println(m.group(1));
                            String sql = "SELECT * from uredaji where id=" + m.group(1);

                            try {

                                conn = DriverManager.getConnection(connURL, ua, ap);
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery(sql);

                                if (!rs.next()) {
                                    System.out.println("OK!");
                                    sql = "insert into uredaji (id,naziv,latitude,longitude) values(" + m.group(1) + ",\"" + m.group(2) + "\"," + m.group(3) + "," + m.group(4) + ")";
                                    //System.out.println(sql);
                                    folder.copyMessages(messages, NWTIS);
                                    System.out.println("KOPIRANO U NWTIS_PORUKE");
                                    stmt.execute(sql);
                                    message.setFlag(Flags.Flag.DELETED, true);
                                    folder.expunge();
                                    System.out.println("PORUKA IZBRISANA IZ INBOXA");
                                    brojDodanihIOT++;
                                    flag = true;
                                } else {
                                    System.out.println("VEĆ POSTOJI TAKAV  UREĐAJ");
                                    folder.copyMessages(messages, NWTISO);
                                    message.setFlag(Flags.Flag.DELETED, true);
                                    folder.expunge();
                                    flag = true;
                                    brojPogresaka++;

                                }
                            } catch (SQLException ex) {
                                System.out.println("Pogreška kod upisa "+ex);
                            }
                            status = false;
                        }

                        pattern = Pattern.compile(sintaksaTEMP);
                        m = pattern.matcher(content);
                        status = m.matches();
                        if (status) {
                            System.out.println("SINTAKSA:TEMP");
                            
                            String sql = "SELECT * from uredaji where id=" + m.group(1);
                            try {
                                
                                conn = DriverManager.getConnection(connURL, ua, ap);
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery(sql);
                                if (rs.next()) {
                                    System.out.println("OK!");
                                    sql = "insert into temperature (id,temp,vrijeme_mjerenja) values(" + m.group(1) + "," + m.group(10) + ",\"" + m.group(2) + "\")";
                                    folder.copyMessages(messages, NWTIS);
                                    System.out.println("KOPIRANO U NWTIS_PORUKE");
                                    stmt.execute(sql);
                                    message.setFlag(Flags.Flag.DELETED, true);
                                    folder.expunge();
                                    System.out.println("PORUKA IZBRISANA IZ INBOXA");
                                    brojMjerenihTEMP++;
                                    flag = true;
                                } else {
                                    System.out.println("NE POSTOJI TAKAV  UREĐAJ");
                                    folder.copyMessages(messages, NWTISO);
                                    System.out.println("KOPIRANO U NWTISO_PORUKE");
                                    message.setFlag(Flags.Flag.DELETED, true);
                                    folder.expunge();
                                    System.out.println("PORUKA IZBRISANA IZ INBOXA");
                                    flag = true;
                                    brojPogresaka++;
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            status = false;
                        }

                        pattern = Pattern.compile(sintaksaEVENT);
                        m = pattern.matcher(content);
                        status = m.matches();
                        if (status) {
                            System.out.println("SINTAKSA:EVENT");
                            String sql = "SELECT * from uredaji where id=" + m.group(1);
                            try {

                                conn = DriverManager.getConnection(connURL, ua, ap);
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery(sql);

                                if (rs.next()) {

                                    sql = "select vrsta from dogadaji_vrste where vrsta=" + m.group(9);
                                    System.out.println(sql);
                                    rs = stmt.executeQuery(sql);
                                    if (rs.next()) {
                                        sql = "insert into dogadaji (id,vrsta,vrijeme_izvrsavanja) values(" + m.group(1) + "," + m.group(9) + ",\"" + m.group(2) + "\")";

                                        stmt.execute(sql);
                                        folder.copyMessages(messages, NWTIS);
                                         System.out.println("KOPIRANO U NWTIS_PORUKE");
                                        message.setFlag(Flags.Flag.DELETED, true);
                                        folder.expunge();
                                        System.out.println("PORUKA IZBRISANA IZ INBOXA");
                                        brojIzvrsenihEVENT++;
                                        flag = true;
                                    } else {
                                        System.out.println("NE POSTOJI TAKAV UREĐAJ");
                                        folder.copyMessages(messages, NWTISO);
                                        message.setFlag(Flags.Flag.DELETED, true);
                                        folder.expunge();
                                        flag = true;
                                        brojPogresaka++;
                                    }
                                } else {
                                    System.out.println("NE POSTOJI TAKAV DOGAĐAJ");
                                    folder.copyMessages(messages, NWTISO);
                                    message.setFlag(Flags.Flag.DELETED, true);
                                    folder.expunge();
                                    flag = true;
                                    brojPogresaka++;
                                }

                            } catch (SQLException ex) {
                                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            status = false;
                        }

                        if (flag == false) {
                            folder.copyMessages(messages, NWTISO);
                            System.out.println("POGREŠNA SINTAKSA");
                            System.out.println("KOPIRANO U NWTISO");
                            message.setFlag(Flags.Flag.DELETED, true);
                            folder.expunge();
                            brojPogresaka++;
                        }

                    } else {
                        folder.copyMessages(messages, NWTISO);
                        System.out.println("PORUKA NIJE U T/P FORMATU ILI SUBJEKT NIJE ISPRAVAN");
                        System.out.println("KOPIRANO U NWTISO");
                        message.setFlag(Flags.Flag.DELETED, true);
                        folder.expunge();
                        brojPogresaka++;

                    }
                }

                redniBrojCiklusa++;

                trajanjeObrade = (int) System.currentTimeMillis() - (int) vrijemePocetka;
                sleep(trajanjeCiklusa * 1000 - trajanjeObrade);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss.zzz");
                Date pocetak = new Date(vrijemePocetka);
                sdf.format(pocetak);
               
                vrijemeZavrsetka = System.currentTimeMillis();
                Date kraj = new Date(vrijemeZavrsetka);
                sdf.format(kraj);
               
                BigDecimal bd = new BigDecimal(redniBrojCiklusa);
                store.close();
                store.connect(server, Integer.valueOf(port), statadr,
                        "654321");
                Folder folder2 = store.getFolder("INBOX");
                int rednibroj = folder2.getMessageCount();
                
                rednibroj++;
                store.close();;

                DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(new Locale("en_GB"));
                formatter = new DecimalFormat("#,##0");
                DecimalFormatSymbols customSymbol = new DecimalFormatSymbols();
                customSymbol.setDecimalSeparator('.');
                customSymbol.setGroupingSeparator('.');
                ((DecimalFormat) formatter).setDecimalFormatSymbols(customSymbol);

                formatter.setGroupingUsed(true);

                String redniBroj = formatter.format(rednibroj);
                redniBroj = String.format("%1$4s", redniBroj);
                System.out.println("Obrada poruka u ciklusu: " + redniBroj);
                

                String subject = statsubject + " " + redniBroj;
                String content = "Obrada započela u: " + pocetak + " "
                        + "Obrada završila u: " + kraj + " "
                        + "Trajanje obrade :" + trajanjeObrade + " "
                        + "Broj poruka : " + brojPoruka + " "
                        + "Broj dodanih Iot : " + brojDodanihIOT + " "
                        + "Broj mjerenih TEMP: " + brojMjerenihTEMP + " "
                        + "Broj izvrsenih EVENT: " + brojIzvrsenihEVENT + " "
                        + "Broj pogresaka:" + brojPogresaka + " ";
                slanjeMaila.salji(korisnik, statadr, subject, content);
                
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException | InterruptedException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
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
