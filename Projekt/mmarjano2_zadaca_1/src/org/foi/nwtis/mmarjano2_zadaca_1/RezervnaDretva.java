/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2_zadaca_1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mmarjano2.konfiguracije.Konfiguracija;

/**
 *
 * @author grupa_2
 */
class RezervnaDretva extends Thread {

    Konfiguracija konf;
    boolean status=false;
    RadnaDretva dretva;
    Socket socket;
    OutputStream os= null;
   
    RezervnaDretva(Konfiguracija konf) {
        this.konf = konf;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        
        while(true){
            
            
            try {
                sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RezervnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(status){
               
                socket=dretva.getSocket();
                try {
                    os=socket.getOutputStream();
                    os.write(" Red pun".getBytes());
                    os.flush();
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(RezervnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                        
                status=false;
            }
          
        }
       

    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public void op(RadnaDretva dretva) throws IOException {

        this.dretva=dretva;
        status = true;
        

    }

}
