/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web.dretve;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

/**
 *
 * @author grupa_2
 */
public class ObradaPoruka extends Thread {

    private boolean prekid_obrade = false;
    private ServletContext sc = null;

    @Override
    public void interrupt() {
        prekid_obrade = true;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        int trajanjeObrade = 0;
        int brojPoruka = 0;
        int brojDodanihIOT = 0;
        int brojMjerenihTEMP = 0;
        int brojIzvrsenihEVENT = 0;
        int brojPogresaka = 0;
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

        BP_Konfiguracija BPkonf = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");

        int redniBrojCiklusa = 0;

        while (!prekid_obrade) {
            // Start the session
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", server);
            Session session = Session.getInstance(properties, null);

            // Connect to the store
            try {
                Store store = session.getStore("imap");
                store.connect(server, korisnik, lozinka);
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
                
//                NWTIS.open(Folder.READ_WRITE);    
//                Message[] messages1 = NWTIS.getMessages();
//                for (int i = 0; i < messages1.length; ++i) {
//                    Message message = messages1[i];
//                    message.setFlag(Flags.Flag.DELETED, true);
//                                    NWTIS.expunge();
//                }
//                NWTISO.open(Folder.READ_WRITE);
//                
//                Message[] messages2 = NWTISO.getMessages();
//                for (int i = 0; i < messages2.length; ++i) {
//                    Message message = messages2[i];
//                    message.setFlag(Flags.Flag.DELETED, true);
//                                    NWTISO.expunge();
//                }
              
                

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

                    if (textos) {
                        String content = (String) message.getContent();

                        content = content.trim();
                        content = content.split(";")[0] + ";";
                        boolean flag = false;
                        Pattern pattern = Pattern.compile(sintaksaADD);
                        Matcher m = pattern.matcher(content);
                        boolean status = m.matches();
                        if (status) {
                            System.out.println("Sintaksa je ADD");
                            System.out.println(m.group(1));
                            String sql = "SELECT * from uredaji where id=" + m.group(1);

                            try {
                                folder.copyMessages(messages, NWTIS);
                                conn = DriverManager.getConnection(connURL, ua, ap);
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery(sql);

                                if (!rs.next()) {
                                    System.out.println("OK!");
                                    sql = "insert into uredaji (id,naziv,latitude,longitude) values(" + m.group(1) + ",\"" + m.group(2) + "\"," + m.group(3) + "," + m.group(4) + ")";
                                    //System.out.println(sql);

                                    
                                    System.out.println("kopirano");
                                    stmt.execute(sql);
                                    message.setFlag(Flags.Flag.DELETED, true);
                                    folder.expunge();
                                    //System.out.println("Poruka izbrisana");
                                    brojMjerenihTEMP++;
                                    flag = true;
                                } else {
                                    //System.out.println("Ima zapis");
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            brojDodanihIOT++;
                            status = false;
                        }

                        pattern = Pattern.compile(sintaksaTEMP);
                        m = pattern.matcher(content);
                        status = m.matches();
                        if (status) {
                            System.out.println("Sintaksa je TEMP");
                            // System.out.println(m.group(1) + " " + m.group(2) + " " + m.group(3));
                            String sql = "SELECT * from uredaji where id=" + m.group(1);
                            try {
                                folder.copyMessages(messages, NWTIS);
                                conn = DriverManager.getConnection(connURL, ua, ap);
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery(sql);
                                if (rs != null) {
                                    System.out.println("OK!");
                                    sql = "insert into temparature (id,temp,vrijeme_mjerenja) values(" + m.group(1) + "," + m.group(10) + ",\"" + m.group(2) + "\")";
                                          System.out.println(sql);
                                    stmt.execute(sql);
                                    
                                    message.setFlag(Flags.Flag.DELETED, true);
                                    folder.expunge();
                                    //     System.out.println("Poruka izbrisana");
                                    brojMjerenihTEMP++;
                                    flag = true;
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
                            //System.out.println("Sintaksa je EVENT");
                            //System.out.println(m.group(1) + " " + m.group(2) + " " + m.group(3));
                            String sql = "SELECT * from uredaji where id=" + m.group(1);
                            try {
                                folder.copyMessages(messages, NWTIS);
                                conn = DriverManager.getConnection(connURL, ua, ap);
                                stmt = conn.createStatement();
                                rs = stmt.executeQuery(sql);
                                if (rs != null) {
                                    //      System.out.println("OK!");
                                    sql = "insert into dogadaji (id,vrsta,vrijeme_izvrsavanja) values(" + m.group(1) + "," + m.group(9) + ",\"" + m.group(2) + "\")";
                                       System.out.println(sql);
                                    stmt.execute(sql);
                                    
                                    message.setFlag(Flags.Flag.DELETED, true);
                                    folder.expunge();
                                    //  System.out.println("Poruka izbrisana");
                                    brojIzvrsenihEVENT++;
                                    flag = true;
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            status = false;
                        }

                        if (flag == false) {
                            folder.copyMessages(messages, NWTISO);
                            System.out.println("kopirano");
                            message.setFlag(Flags.Flag.DELETED, true);
                            folder.expunge();
                            brojPogresaka++;
                        }

                    } else {
                        folder.copyMessages(messages, NWTISO);
                        System.out.println("kopirano");
                        message.setFlag(Flags.Flag.DELETED, true);
                        folder.expunge();
                        brojPogresaka++;

                    }
                }
                redniBrojCiklusa++;
                System.out.println("Obrada prouka u ciklusu: " + redniBrojCiklusa);
                sleep(trajanjeCiklusa * 1000 - trajanjeObrade);

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
