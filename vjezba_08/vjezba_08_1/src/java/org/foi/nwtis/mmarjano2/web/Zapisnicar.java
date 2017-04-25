/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matija
 */
public class Zapisnicar extends Thread {

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        //TODO infinite loop
        while (true) {
            try {
                System.out.println("Sada je:" + new Date());

                this.sleep(10000);
            } catch (InterruptedException ex) {
                System.out.println("Interruptano");
                break;
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
