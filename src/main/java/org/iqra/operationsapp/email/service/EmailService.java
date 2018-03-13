/**
 * 
 */
package org.iqra.operationsapp.email.service;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.iqra.operationsapp.email.props.EmailProperties;
import org.iqra.operationsapp.helper.PopulateDetailsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Abdul 06-Mar-2018
 * 
 */

@Component
public class EmailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmailProperties emailProperties;

	@Autowired
	private PopulateDetailsHelper populateDetailsHelper;

	public void sendEmail(String dateStr, String messageDetail, String mailSubject, String comments)
			throws IOException {

		logger.debug("Begin : EmailService --> sendEmail()");

		String changeTriggeredBy = populateDetailsHelper.getLoggedInUserName();
		String emailHost = emailProperties.getEmailHost();
		String reciverEmailId = emailProperties.getToReciepients();
		String senderEmailId = emailProperties.getFromEmail();
		String port = emailProperties.getEmailPort();
		String userNameToLoginIn = emailProperties.getUsername();
		String passwordToLoginIn = emailProperties.getPassword();
		// String fileLocation = prop.getProperty("fileLocation");
		String emailMessage = emailProperties.getEmailMessage();
		if (userNameToLoginIn == null) {
			userNameToLoginIn = "";
		}
		if (passwordToLoginIn == null) {
			passwordToLoginIn = "";
		}
		String to = reciverEmailId;

		String from = senderEmailId;

		final String username = userNameToLoginIn;
		final String password = passwordToLoginIn;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", emailHost);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			message.setSubject(mailSubject + " " + changeTriggeredBy.toUpperCase() + " -- " + comments);

			String messageDetails = emailMessage + messageDetail;
			message.setContent(messageDetails, "text/html");

			Transport.send(message);
		} catch (Exception e) {
			System.out.println("Exception occured whiel sending mail.");
		}

		logger.debug("End : EmailService --> sendEmail()");
	}

}
