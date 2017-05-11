/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.foi.nwtis.mmarjano2.KonfiguracijaBP;

/**
 * Pomoćna klasa za baratanje SQL upitima
 * @author Matija Marjanović
 */
public class SqlRunner {

    KonfiguracijaBP BPkonf;
    ServletContext sc =null;
    
    /**
     * Konstruktor klsae SqlRunner
     * @param sc ServletContext
     */
    public SqlRunner(ServletContext sc) {
       this.sc=sc;
    }
    
    /**
     * Metoda za dohvaćanje pomoću SQL upita
     * @param sql String sql upit
     * @return  ResultSet
     */
    public ResultSet izvrsiSQL(String sql) {

        try {
            
            try {
                BPkonf = (KonfiguracijaBP) sc.getAttribute("BP_Konfig");
            } catch (Exception e) {
                 BPkonf=SCHelper.getKonfBP();
            }
        
            
            String connURL = BPkonf.getServerDatabase() + BPkonf.getUserDatabase();
            System.out.println(connURL);
            Connection conn = null;
            Statement stmt = null;
            ResultSet resultSet = null;
            String ua = BPkonf.getUserUsername();
            String ap = BPkonf.getUserPassword();
            System.out.println(ua);
            System.out.println(ap);

            try {
                Class.forName(BPkonf.getDriverDatabase());
            } catch (ClassNotFoundException ex) {
                System.out.println("Nemoguće čitati driver" + ex.getMessage());
                return null;
            }

            conn = DriverManager.getConnection(connURL, ua, ap);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            return resultSet;

        } catch (SQLException ex) {
            Logger.getLogger(SqlRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metoda za izvršavanje upisa u bazu
     * 
     * @param sql SQL upit
     */
    public void doSQL(String sql) {

        try {
            KonfiguracijaBP BPkonf = (KonfiguracijaBP) sc.getAttribute("BP_Konfig");

            String connURL = BPkonf.getServerDatabase() + BPkonf.getUserDatabase();
            Connection conn = null;
            Statement stmt = null;
            
            String ua = BPkonf.getUserUsername();
            String ap = BPkonf.getUserPassword();

            try {
                Class.forName(BPkonf.getDriverDatabase());
            } catch (ClassNotFoundException ex) {
                System.out.println("Nemoguće čitati driver" + ex.getMessage());
                
            }

            conn = DriverManager.getConnection(connURL, ua, ap);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
           

        } catch (SQLException ex) {
            Logger.getLogger(SqlRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        

}
}
