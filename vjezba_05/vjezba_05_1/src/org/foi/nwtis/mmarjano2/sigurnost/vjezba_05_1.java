/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.sigurnost;

import java.io.File;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mmarjano2.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author Matija
 */
public class vjezba_05_1 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Nije dobar broj parametara");
            return;

        }
        try {
            File datoteka = new File(args[0]);
            if (!datoteka.exists()) {
                System.out.println("Ne postoji datoteka konfiguracije");
                return;
            }
            Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
            System.out.println("Postavka: port=" + konfig.dajPostavku("port"));
        } catch (NemaKonfiguracije ex) {
            System.err.println("Problem s konfiguracijom" + ex.getMessage());
            return;
        } catch (NeispravnaKonfiguracija ex) {
            System.err.println("Problem s neispravnom konfiguracijom" + ex.getMessage());
            return;
        }
    }
}
