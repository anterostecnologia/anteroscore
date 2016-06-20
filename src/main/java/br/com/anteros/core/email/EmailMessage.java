package br.com.anteros.core.email;

import java.util.ArrayList;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailMessage {
	private String emailTo;
	private String emailFrom;
	private String subject;
	private String textMessage;
	private ArrayList<EmailAttachment> attachments;

	public EmailMessage() {
		attachments = new ArrayList<EmailAttachment>();
	}

	public InternetAddress[] getEmailTO() throws AddressException {
		String str[] = emailTo.split(";");
		if (str.length > 0) {
			return new InternetAddress[]{new InternetAddress(str[0])};
		}
		return null;
	}

	public InternetAddress[] getEmailCC() throws AddressException {
		String str[] = emailTo.split(";");
		if (str.length > 0) {
			InternetAddress adds[] = new InternetAddress[str.length - 1];
			for (int i = 1; i < str.length; i++)
				adds[i-1] = new InternetAddress(str[i]);

			return adds;
		}
		return null;
	}


	public void addAttachament(EmailAttachment anexo) {
		this.getAttachments().add(anexo);
	}

	public void removeAttachament(EmailAttachment anexo) {
		this.getAttachments().remove(anexo);
	}

	public void removeAttachament(int index) {
		this.getAttachments().remove(index);
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	public ArrayList<EmailAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(ArrayList<EmailAttachment> attachments) {
		this.attachments = attachments;
	}

}