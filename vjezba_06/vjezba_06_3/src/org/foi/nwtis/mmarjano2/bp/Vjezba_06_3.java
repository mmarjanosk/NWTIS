/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.bp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mmarjano2.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author Matija
 */
public class Vjezba_06_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        if(args.length !=1){
            System.out.println("Pogrešan broj argumenata");
            return;
        }
        BP_Konfiguracija bp = new BP_Konfiguracija(args[0]);
        if(bp.getStatus()){
            System.out.println(bp.getDriverDatabase());
            String connURL= bp.getServerDatabase()+bp.getUserDatabase();
            Connection conn=null;
            Statement stmt=null;
            ResultSet rs=null;
            String ua= bp.getAdminUsername();
            String ap= bp.getAdminPassword();
            String sql= "CREATE TABLE mmarjano2 (kor_ime char(10) NOT NULL DEFAULT '',zapis varchar(250) NOT NULL DEFAULT '')";
            try {
                Class.forName(bp.getDriverDatabase());
            } catch (ClassNotFoundException ex) {
                System.out.println("Nemoguće čitati driver"+ex.getMessage());
                return;
            }
            try {
                conn = DriverManager.getConnection(connURL, ua, ap);
                stmt =conn.createStatement();
                stmt.executeUpdate(sql);
                /* while(rs.next()){
                    String ime = rs.getString("ime");
                    String prezime = rs.getString("prezime");
                    System.out.println(ime+" "+prezime);
                }*/
               
            } catch (SQLException ex) {
                System.out.println("Greška kod spajanja na bazu"+ex.getMessage());
            }
            finally{
                if(rs!=null){
                    rs.close();
                }
                if(stmt !=null){
                    stmt.close();
                }
                if(conn !=null){
                    conn.close();
                }
            }
        }
    }
    
}
