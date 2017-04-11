/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.io.OutputStream;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;
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
public class AdminKomandeTest {
    
    public AdminKomandeTest() {
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
     * Test of ZaustaviServer method, of class AdminKomande.
     */
    @Test
    public void testZaustaviServer() {
        System.out.println("ZaustaviServer");
        AdminKomande instance = new AdminKomande();
        instance.ZaustaviServer();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of PokreniServer method, of class AdminKomande.
     */
    
    @Test
    public void testPokreniServer() {
        System.out.println("PokreniServer");
        AdminKomande instance = new AdminKomande();
        instance.PokreniServer();
       
       
    }

    /**
     * Test of TestirajServer method, of class AdminKomande.
     */
    
    @Test
    public void testTestirajServer() {
        System.out.println("TestirajServer");
        AdminKomande instance = new AdminKomande();
        boolean expResult = false;
        boolean result = instance.TestirajServer();
        assertEquals(expResult, result);
        
    }

   
    
    
}
