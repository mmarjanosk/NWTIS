/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mmarjano2.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Matija
 */
public class SlanjeMaila {

    public SlanjeMaila() {
    }

    public void salji(String from, String to, String subject , String text ) throws MessagingException {
        java.util.Properties properties = System.getProperties();
        Session session = Session.getInstance(properties, null);
        MimeMessage message = new MimeMessage(session);
        try {
            Address a_from = new InternetAddress(from);
            Address a_to = new InternetAddress(to);        
            message.setFrom(a_from);
            message.setRecipient(Message.RecipientType.TO, a_to);
            message.setSubject(subject);
            message.setText(text);
            message.setHeader("Content-type", "text/plain");
            Transport.send(message);
            ;
        } catch (AddressException ex) {
            Logger.getLogger(SlanjeMaila.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
