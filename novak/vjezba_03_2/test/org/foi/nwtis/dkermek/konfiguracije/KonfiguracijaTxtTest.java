/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkermek.konfiguracije;

import org.foi.nwtis.mmarjano2.konfiguracije.KonfiguracijaTxt;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author grupa_1
 */
public class KonfiguracijaTxtTest {
    
    public KonfiguracijaTxtTest() {
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
     * Test of ucitajKonfiguraciju method, of class KonfiguracijaTxt.
     */
    @Test
    public void testUcitajKonfiguraciju() throws Exception {
        System.out.println("ucitajKonfiguraciju");
        KonfiguracijaTxt instance = null;
        instance.ucitajKonfiguraciju();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ucitajKonfiguraciju method, of class KonfiguracijaTxt.
     */
    @Test
    public void testUcitajKonfiguraciju_String() throws Exception {
        System.out.println("ucitajKonfiguraciju");
        String datoteka = "";
        KonfiguracijaTxt instance = null;
        instance.ucitajKonfiguraciju(datoteka);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of spremiKonfiguraciju method, of class KonfiguracijaTxt.
     */
    @Test
    public void testSpremiKonfiguraciju() throws Exception {
        System.out.println("spremiKonfiguraciju");
        KonfiguracijaTxt instance = null;
        instance.spremiKonfiguraciju();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of spremiKonfiguraciju method, of class KonfiguracijaTxt.
     */
    @Test
    public void testSpremiKonfiguraciju_String() throws Exception {
        System.out.println("spremiKonfiguraciju");
        String datoteka = "";
        KonfiguracijaTxt instance = null;
        instance.spremiKonfiguraciju(datoteka);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
