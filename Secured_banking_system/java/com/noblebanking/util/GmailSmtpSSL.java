package com.noblebanking.util;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailSmtpSSL {

    public GmailSmtpSSL(String username,String password) {
        usern = username;
        pass  = password;



        props = new Properties();


        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.user", usern);

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SMTPAuthenticator auth = new SMTPAuthenticator();
        session = Session.getDefaultInstance(props, auth);

    }


    public void sendMailTo(String to, String sub, String body)
                                                    throws MessagingException {


         try {
             Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress(usern));
             message.setRecipients(Message.RecipientType.TO, 
                                                    InternetAddress.parse(to));
             message.setSubject(sub);
             message.setText(body);
             Transport transport = session.getTransport("smtps");

             transport.connect("smtp.gmail.com", 465, usern, pass);
             Transport.send(message);
             transport.close();
         }

         catch(MessagingException me) {
             throw new MessagingException(me.toString());
         }
    }



    private String usern; 
    private String pass;
    private Session session;
    private static Properties props;

    private class SMTPAuthenticator extends Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(usern, pass);
        }
    }

}