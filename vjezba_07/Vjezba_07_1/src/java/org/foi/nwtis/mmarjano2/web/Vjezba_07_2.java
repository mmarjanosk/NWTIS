/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.mmarjano2.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author Matija
 */
public class Vjezba_07_2 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Vjezba_07_2</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Vjezba_07_2 at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");

            String path = this.getServletContext().getRealPath("/WEB-INF") + java.io.File.separator;
            String datoteka = path + this.getInitParameter("konfiguracija");
            BP_Konfiguracija bp = new BP_Konfiguracija(datoteka);
            if (bp.getStatus()) {
                System.out.println(bp.getDriverDatabase());
                String connURL = bp.getServerDatabase() + bp.getUserDatabase();
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;
                String ua = bp.getUserUsername();
                String ap = bp.getUserPassword();
                String sql = "SELECT ime,prezime FROM polaznici";
                try {
                    Class.forName(bp.getDriverDatabase());
                } catch (ClassNotFoundException ex) {
                    out.println("Nemoguće čitati driver" + ex.getMessage());
                    return;
                }
                try {
                    conn = DriverManager.getConnection(connURL, ua, ap);
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        String ime = rs.getString("ime");
                        String prezime = rs.getString("prezime");
                        out.println(ime + " " + prezime + "</br>");
                    }

                } catch (SQLException ex) {
                    out.println("Greška kod spajanja na bazu");
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Vjezba_07_2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Vjezba_07_2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Vjezba_07_2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
