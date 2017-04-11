/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import java.util.LinkedList;
import java.util.Queue;


/**
 *
 * @author Matija
 */
public class RedCekanja<T> {
    public static Queue<RadnaDretva> red = new LinkedList<RadnaDretva>();
    int kapacitet=7;
    Thread thread;
    

    public RedCekanja(){
        
       
    }

    public synchronized void put(RadnaDretva dretva) throws InterruptedException {
        while(red.size() == kapacitet) {
            thread.notify();  
        }

        red.add(dretva);
         // notifyAll() for multiple producer/consumer threads
    }

    public synchronized void take(RadnaDretva dretva) throws InterruptedException {
        while(red.isEmpty()) {
            wait();
        }

        red.remove(dretva);
        notifyAll(); // notifyAll() for multiple producer/consumer threads
        
    }
}
