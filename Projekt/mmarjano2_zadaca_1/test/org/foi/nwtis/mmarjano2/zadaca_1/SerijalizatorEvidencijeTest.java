/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.io.FileNotFoundException;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mmarjano2.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mmarjano2.konfiguracije.NemaKonfiguracije;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Matija
 */
public class SerijalizatorEvidencijeTest {
    
    public SerijalizatorEvidencijeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
     
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of interrupt method, of class SerijalizatorEvidencije.
     */
    
    @Test
    public void testInterrupt() throws FileNotFoundException, NemaKonfiguracije, NeispravnaKonfiguracija {
        System.out.println("interrupt");
        Konfiguracija konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju("NWTiS_mmarjano2_zadaca_1.xml");
        SerijalizatorEvidencije instance = new SerijalizatorEvidencije(konf);
        instance.interrupt();
        
    }
   
    @Test
    public void testSerialize() throws FileNotFoundException, NemaKonfiguracije, NeispravnaKonfiguracija {
        System.out.println("serialize");
        Evidencija e = new Evidencija();
        Konfiguracija konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju("NWTiS_mmarjano2_zadaca_1.xml");
        SerijalizatorEvidencije instance = new SerijalizatorEvidencije(konf);
        instance.serialize(e);
        
        
    }
    
}
