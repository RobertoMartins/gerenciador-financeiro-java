package br.com.javaparaweb.financeiro.web.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.javaparaweb.financeiro.util.UtilException;

public class GmailUtil {
	//https://developers.google.com/gmail/api/guides/sending
	
    public void enviarEmail(String de, String para, String assunto, String mensagem) throws UtilException {
		 Properties prop = new Properties();
                 String password = "WEB2projeto";
	prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(de, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(de));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(para)
            );
            message.setSubject(assunto);
            message.setText(mensagem);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        }
}