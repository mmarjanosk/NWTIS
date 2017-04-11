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

/**
 *
 * @author Matija
 */
public class EvidencijaTest {

    public EvidencijaTest() {
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
     * Test of getUkupnoZahtjeva method, of class Evidencija.
     */
    @Test
    public void testGetUkupnoZahtjeva() {
        System.out.println("getUkupnoZahtjeva");
        Evidencija instance = new Evidencija();
        instance.setBrojPrekinutihZahtjeva();
        instance.setBrojUspjesnihZahtjeva();
        int expResult = 2;
        int result = instance.getUkupnoZahtjeva();
        assertEquals(expResult, result);

    }

    /**
     * Test of getZadnjaAdresa method, of class Evidencija.
     */
    @Test
    public void testGetZadnjaAdresa() {
        System.out.println("getZadnjaAdresa");
        Evidencija instance = new Evidencija();
        instance.setZadnjaAdresa("a");
        String expResult = "a";
        String result = instance.getZadnjaAdresa();
        assertEquals(expResult, result);

    }

    /**
     * Test of setZadnjaAdresa method, of class Evidencija.
     */
    @Test
    public void testSetZadnjaAdresa() {
        System.out.println("setZadnjaAdresa");
        String zadnjaAdresa = "TEST";
        Evidencija instance = new Evidencija();
        instance.setZadnjaAdresa(zadnjaAdresa);
        String expected = instance.getZadnjaAdresa();
        assertEquals(expected, zadnjaAdresa);

    }

    /**
     * Test of getSize method, of class Evidencija.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        Evidencija instance = new Evidencija();
        instance.setZahtjeviZaAdrese("TEST");
        int expResult = 1;
        int result = instance.getSize();
        assertEquals(expResult, result);

    }

    /**
     * Test of getState method, of class Evidencija.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        String adresa = "TEST";
        Evidencija instance = new Evidencija();
        instance.setZahtjeviZaAdrese("TEST");
        boolean expResult = true;
        boolean result = instance.getState(adresa);
        assertEquals(expResult, result);

    }

    /**
     * Test of contains method, of class Evidencija.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        String adresa = "TEST";
        Evidencija instance = new Evidencija();
        instance.setZahtjeviZaAdrese("TEST");
        boolean expResult = true;
        boolean result = instance.contains(adresa);
        assertEquals(expResult, result);

    }

    /**
     * Test of getBrojUspjesnihZahtjeva method, of class Evidencija.
     */
    @Test
    public void testGetBrojUspjesnihZahtjeva() {
        System.out.println("getBrojUspjesnihZahtjeva");
        Evidencija instance = new Evidencija();
        instance.setBrojUspjesnihZahtjeva();
        int expResult = 1;
        int result = instance.getBrojUspjesnihZahtjeva();
        assertEquals(expResult, result);

    }

    /**
     * Test of setBrojUspjesnihZahtjeva method, of class Evidencija.
     */
    @Test
    public void testSetBrojUspjesnihZahtjeva() {
        System.out.println("setBrojUspjesnihZahtjeva");
        Evidencija instance = new Evidencija();
        instance.setBrojUspjesnihZahtjeva();
        assertEquals(1, instance.getBrojUspjesnihZahtjeva());

    }

    /**
     * Test of getBrojPrekinutihZahtjeva method, of class Evidencija.
     */
    @Test
    public void testGetBrojPrekinutihZahtjeva() {
        System.out.println("getBrojPrekinutihZahtjeva");
        Evidencija instance = new Evidencija();
        instance.setBrojPrekinutihZahtjeva();
        int expResult = 1;
        int result = instance.getBrojPrekinutihZahtjeva();
        assertEquals(expResult, result);

    }

    /**
     * Test of setBrojPrekinutihZahtjeva method, of class Evidencija.
     */
    @Test
    public void testSetBrojPrekinutihZahtjeva() {
        System.out.println("setBrojPrekinutihZahtjeva");
        Evidencija instance = new Evidencija();
        instance.setBrojPrekinutihZahtjeva();
        int expResult = 1;
        int result = instance.getBrojPrekinutihZahtjeva();
        assertEquals(expResult, result);

    }

    /**
     * Test of setZahtjeviZaAdrese method, of class Evidencija.
     */
    @Test
    public void testSetZahtjeviZaAdrese() {
        System.out.println("setZahtjeviZaAdrese");
        String adresa = "TEST";
        Evidencija instance = new Evidencija();
        instance.setZahtjeviZaAdrese(adresa);
        boolean expected = true;
        assertEquals(expected, instance.contains(adresa));

    }

    /**
     * Test of getBrojZadnjeDretve method, of class Evidencija.
     */
    @Test
    public void testGetBrojZadnjeDretve() {
        System.out.println("getBrojZadnjeDretve");
        Evidencija instance = new Evidencija();
        instance.setBrojZadnjeDretve(32245);
        int expResult = 32245;
        int result = instance.getBrojZadnjeDretve();
        assertEquals(expResult, result);

    }

    /**
     * Test of setBrojZadnjeDretve method, of class Evidencija.
     */
    @Test
    public void testSetBrojZadnjeDretve() {
        System.out.println("setBrojZadnjeDretve");
        int brojZadnjeDretve = 7892344;
        Evidencija instance = new Evidencija();
        instance.setBrojZadnjeDretve(brojZadnjeDretve);
        assertEquals(brojZadnjeDretve, instance.getBrojZadnjeDretve());

    }

}
