/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.konfiguracije.bp;

import java.util.Map;
import java.util.Properties;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mmarjano2.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author Matija
 */
public class BP_Konfiguracija implements BP_Sucelje {

    Konfiguracija konfig;
    private boolean status = false;
    private String serverDB;
    private String adminUsername;
    private String adminPassword;
    private String adminDatabase;
    private String userUsername;
    private String userPassword;
    private String userDatabase;
    private static final String drive_database = "driver.database";
    private String driverDatabase;

    public BP_Konfiguracija(String datoteka) {
        try {
            konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            status = true;
            serverDB = konfig.dajPostavku("server.database");
            adminUsername = konfig.dajPostavku("admin.username");
            adminPassword = konfig.dajPostavku("admin.password");
            adminDatabase = konfig.dajPostavku("admin.database");

            userUsername = konfig.dajPostavku("user.username");
            userPassword = konfig.dajPostavku("user.password");
            userDatabase = konfig.dajPostavku("user.database");
            driverDatabase=getDriverDatabase(serverDB);

        } catch (NemaKonfiguracije ex) {
            System.out.println("Nema konfiguracije" + ex.getMessage());
        } catch (NeispravnaKonfiguracija ex) {
            System.out.println("Neispravna konfiguracije" + ex.getMessage());
        }

    }

    public boolean getStatus() {
        return this.status;
    }

    @Override
    public String getAdminDatabase() {
        return this.adminDatabase;
    }

    @Override
    public String getAdminPassword() {
        return this.adminPassword;
    }

    @Override
    public String getAdminUsername() {
        return this.adminUsername;
    }

    @Override
    public String getDriverDatabase() {
        return this.driverDatabase;
    }

    @Override
    public String getDriverDatabase(String bp_url) {
        String[] url_parts = bp_url.split(":");
        if (url_parts == null || url_parts.length < 3) {
            return null;
        }
        String driver = konfig.dajPostavku(drive_database + "." + url_parts[1]);
        return driver;
    }

    @Override
    public Properties getDriversDatabase() {
        Properties drivers = new Properties();
        for (Map.Entry<Object, Object> entry : konfig.dajSvePostavke().entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith(drive_database)) {
                drivers.put(key, value);
            }
        }
        return drivers;
    }

    @Override
    public String getServerDatabase() {
        return this.serverDB;
    }

    @Override
    public String getUserDatabase() {
        return this.userDatabase;
    }

    @Override
    public String getUserPassword() {
        return this.userPassword;
    }

    @Override
    public String getUserUsername() {
        return this.userUsername;
    }

}
