package br.com.javaparaweb.financeiro.web.util;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.javaparaweb.financeiro.util.UtilException;

public class EmailUtil {
	public void enviarEmail(String de, String para, String assunto, String mensagem) throws UtilException {
		try {
			Context initialContext = null;
                    try {
                        initialContext = new InitialContext();
                    } catch (NamingException ex) {
                        Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }
			Context envContext = null;
                    try {
                        envContext = (Context) initialContext.lookup("java:comp/env");
                    } catch (NamingException ex) {
                        Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }
			Session session = null;
                    try {
                        session = (Session) envContext.lookup("mail/Session");
                    } catch (NamingException ex) {
                        Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }       
			SimpleEmail email = new SimpleEmail();
			email.setMailSession(session);
			if (de != null) {
				email.setFrom(de);
			} else {
				Properties p = session.getProperties();
				email.setFrom(p.getProperty("mail.smtp.user"));
			}
			email.addTo(para);
			email.setSubject(assunto);
			email.setMsg(mensagem);
			email.setSentDate(new Date());
			email.send();
		} catch (EmailException e) {
			throw new UtilException(e);
		}
	}
}