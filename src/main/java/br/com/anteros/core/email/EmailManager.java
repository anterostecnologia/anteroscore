package br.com.anteros.core.email;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailManager {

	private Properties properties;
	private EmailAuthenticator authenticator;
	private EmailMessage emailMessage;
	private boolean confirmationReceipt = false;
	private boolean readingConfirmation = false;

	public EmailManager(Properties propriedades,
			EmailAuthenticator autenticadorEmail) {
		this.properties = propriedades;
		this.authenticator = autenticadorEmail;
	}

	public EmailManager(Properties propriedades,
			EmailAuthenticator autenticadorEmail,
			boolean confirmationReceipt, boolean readingConfirmation) {
		this.properties = propriedades;
		this.authenticator = autenticadorEmail;
		this.confirmationReceipt = confirmationReceipt;
		this.readingConfirmation = readingConfirmation;
	}

	public void send(EmailMessage emailMessage) throws NoSuchProviderException,
			MessagingException {
		this.emailMessage = emailMessage;

		Session session = Session.getInstance(properties, authenticator);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(this.getEmailMessage().getEmailFrom()));
		InternetAddress[] to = this.emailMessage.getEmailTO();
		InternetAddress[] cc = this.emailMessage.getEmailCC();
		if (readingConfirmation)
			message.addHeader("Disposition-Notification-To",
					this.emailMessage.getEmailFrom());
		if (confirmationReceipt)
			message.addHeader("Return-Receipt-To", this.emailMessage.getEmailFrom());

		message.setRecipients(Message.RecipientType.TO, to);
		if (cc != null)
			message.setRecipients(javax.mail.Message.RecipientType.CC, cc);

		message.setSubject(this.emailMessage.getSubject());
		message.setSentDate(new Date());

		Multipart body = new MimeMultipart();

		MimeBodyPart bodyText = new MimeBodyPart();
		bodyText.setContent(this.getEmailMessage().getTextMessage(), "text/html; charset=utf-8");

		body.addBodyPart(bodyText);

		if (this.emailMessage.getAttachments().size() > 0) {

			InternetHeaders headers = new InternetHeaders();
			for (EmailAttachment anexo : this.emailMessage.getAttachments()) {
				headers.addHeader("Content-Type", anexo.getContentType());
				MimeBodyPart attach = new MimeBodyPart();
				attach.setDataHandler(new DataHandler(new ByteArrayDataSource(
						anexo.getContent(), anexo.getContentType())));
				attach.setFileName(anexo.getName());
				body.addBodyPart(attach);
			}
		}
		
		message.setContent(body);

		Transport.send(message);
	}

	public void showMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("De: ");
		sb.append(emailMessage.getEmailFrom());
		sb.append("\n");
		sb.append("Para: ");
		sb.append(emailMessage.getEmailTo());
		sb.append("\n");
		sb.append("Assunto: ");
		sb.append("\n\n");
		sb.append("Conte\372do: <OK>");
		for (EmailAttachment anexo : emailMessage.getAttachments())
			sb.append("  Anexo-> " + anexo.toString());
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	

	public boolean isConfirmationReceipt() {
		return confirmationReceipt;
	}

	public void setConfirmationReceipt(boolean confirmationReceipt) {
		this.confirmationReceipt = confirmationReceipt;
	}

	public boolean isReadingConfirmation() {
		return readingConfirmation;
	}

	public void setReadingConfirmation(boolean readingConfirmation) {
		this.readingConfirmation = readingConfirmation;
	}

	public EmailAuthenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(EmailAuthenticator authenticator) {
		this.authenticator = authenticator;
	}

	public EmailMessage getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(EmailMessage emailMessage) {
		this.emailMessage = emailMessage;
	}

}