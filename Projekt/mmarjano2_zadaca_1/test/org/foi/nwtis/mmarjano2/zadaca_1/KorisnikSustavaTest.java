/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

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
public class KorisnikSustavaTest {
    
    public KorisnikSustavaTest() {
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
     * Test of argMatcher method, of class KorisnikSustava.
     */
    @Test
    public void testArgMatcher() throws Exception {
        System.out.println("argMatcher");
        String poruka = "-admin -server localhost -port 8000 -u admin -p 654321 -stop";
        KorisnikSustava.argMatcher(poruka);
        
        poruka = "-admin -server localhost -port 8000 -u admin -p 654321 -pause";
        KorisnikSustava.argMatcher(poruka);
        
        poruka = "-admin -server localhost -port 8000 -u admin -p 654321 -start";
        KorisnikSustava.argMatcher(poruka);
        
        poruka = "-admin -server localhost -port 8000 -u admin -p 654321 -stat";
        KorisnikSustava.argMatcher(poruka);
        
        poruka = "-user -s localhost -port 8000 -u pero -a 4";
        KorisnikSustava.argMatcher(poruka);
        
        poruka = "-user -s localhost -port 8000 -u pero -t 4";
        KorisnikSustava.argMatcher(poruka);
        
        poruka = "-user -s localhost -port 8000 -u pero -w 4";
        KorisnikSustava.argMatcher(poruka);
        
        poruka = "Test za neispravan unos";
        KorisnikSustava.argMatcher(poruka);
       
    }

  
    
}
