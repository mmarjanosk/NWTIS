/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.zadaca_1;

import com.sun.javafx.font.FontConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Matija
 */
public class KlijentKomande {

    public KlijentKomande() {
    }
    
    public void add(String adresa, ProvjeraAdresa pa,RadnaDretva dretva,Evidencija evid){
        pa.provjeriAdresu(adresa, dretva,evid);
    }
    
    
    public boolean testAdrese(String adresa,Evidencija e){
       
       if(e.getState(adresa)){
           return true;
       }
       else {
           return false;
       }
    }
    
    public void waitsec(RadnaDretva dretva,String sec) throws InterruptedException, IOException{
        int secs = Integer.valueOf(sec);
        long milis=secs*1000;
        
        Socket socket = dretva.getSocket();
        OutputStream os;
        os=socket.getOutputStream();
        
        try {
            dretva.sleep(milis);
            os.write("OK".getBytes());
            os.flush();
            os.close();
        } catch (InterruptedException e) {
           
        }
dretva.sleep(milis);
    }
}
