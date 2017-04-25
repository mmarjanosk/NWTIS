/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Matija
 */
public class Kontroler extends HttpServlet {

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

        String zahtjev = request.getServletPath();
        String odrediste = null;

        switch (zahtjev) {
            case "/Kontroler":
                odrediste = "/jsp/index.jsp";
                break;

            case "/RegistracijaKorisnika":
                odrediste = "/jsp/registracijaKorisnika.jsp";
                break;

            case "/DodajKorisnika":
                odrediste = "/DodajKorisnika";
                break;

            case "/":
                odrediste = "/jsp/index.jsp";
                break;

            case "/AzurirajKorisnika":
                odrediste = "/AzurirajKorisnika";
                break;

            case "/IspisKorisnika_1":
                odrediste = "/admin/ispisKorisnika_1.jsp";
                break;

            case "/IspisKorisnika_2":
                odrediste = "/admin/ispisKorisnika_2.jsp";
                break;

            case "/IspisKorisnika_3":
                odrediste = "/admin/ispisKorisnika_3.jsp";
                break;
        }

        if (odrediste == null) {
            System.out.println("Problem s loadanjem");
            return;
        }
        String kontekst = request.getContextPath();
        //odrediste=kontekst+odrediste;
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(odrediste);
        rd.forward(request, response);
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
