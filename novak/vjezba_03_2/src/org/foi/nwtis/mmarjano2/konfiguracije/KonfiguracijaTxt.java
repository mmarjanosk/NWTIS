/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.konfiguracije;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grupa_1
 */
public class KonfiguracijaTxt extends KonfiguracijaApstraktna {

    public KonfiguracijaTxt(String datoteka) {
        super(datoteka);
    }

    @Override
    public void ucitajKonfiguraciju() throws NemaKonfiguracije, NeispravnaKonfiguracija {
        this.obrisiSvePostavke();
        if(this.datoteka == null || this.datoteka.length() == 0) {
            throw new NemaKonfiguracije("Neispravno ime datoteke.");
        }        
        File f = new File(this.datoteka);
        if(f.exists() && f.isFile()) {
            try {
                this.postavke.load(new FileInputStream(f));
            } catch (IOException ex) {
                Logger.getLogger(KonfiguracijaTxt.class.getName()).log(Level.SEVERE, null, ex);
                throw new NeispravnaKonfiguracija("Problem kod čitanja datoteke");
            }
        } else {
            throw new NemaKonfiguracije("Ne postoji datoteka.");            
        }
    }

    @Override
    public void ucitajKonfiguraciju(String datoteka) throws NemaKonfiguracije, NeispravnaKonfiguracija {
        this.obrisiSvePostavke();
        if(datoteka == null || datoteka.length() == 0) {
            throw new NemaKonfiguracije("Neispravno ime datoteke.");
        }        
        File f = new File(datoteka);
        if(f.exists() && f.isFile()) {
            try {
                this.postavke.load(new FileInputStream(f));
            } catch (IOException ex) {
                Logger.getLogger(KonfiguracijaTxt.class.getName()).log(Level.SEVERE, null, ex);
                throw new NeispravnaKonfiguracija("Problem kod čitanja datoteke.");
            }
        } else {
            throw new NemaKonfiguracije("Ne postoji datoteka.");            
        }
    }

    @Override
    public void spremiKonfiguraciju() throws NemaKonfiguracije, NeispravnaKonfiguracija {
        if(this.datoteka == null || this.datoteka.length() == 0) {
            throw new NemaKonfiguracije("Neispravno ime datoteke.");
        } 
        try {
            this.postavke.store(new FileOutputStream(this.datoteka), "NWTiS - dkermek - "
                    + new java.util.Date());
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new NeispravnaKonfiguracija("Problem kod spremanje datoteke.");
        }
    }

    @Override
    public void spremiKonfiguraciju(String datoteka) throws NemaKonfiguracije, NeispravnaKonfiguracija {
        if(datoteka == null || datoteka.length() == 0) {
            throw new NemaKonfiguracije("Neispravno ime datoteke.");
        }        
        try {
            this.postavke.store(new FileOutputStream(datoteka), "NWTiS - dkermek - "
                    + new java.util.Date());
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new NeispravnaKonfiguracija("Problem kod spremanje datoteke.");
        }
    }
    
}
