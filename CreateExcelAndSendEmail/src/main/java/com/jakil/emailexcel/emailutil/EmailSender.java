package com.jakil.emailexcel.emailutil;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Value("${spring.mail.host}")
	private String smtpHost;
	@Value("${spring.mail.port}")
	private String smtpPort;
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean smtpAuth;
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private boolean starttls;
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;

	@Value("${mail.from}")
	private String fromAddr;
	@Value("${mail.to}")
	private String toAddr;
	@Value("${mail.cc}")
	private String ccAddr;
	@Value("${mail.subject}")
	private String subject;
	@Value("${mail.body}")
	private String mailBody;

	public void sendMail(List<EmailAttachmentModel> emailAttachments) throws MessagingException {

		// sets SMTP server properties
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.starttls.enable", starttls);
//		props.put("mail.user", username);
//		props.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		Session session = Session.getInstance(props, auth);

		// creates a new e-mail message
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddr));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
		message.setRecipient(RecipientType.CC, new InternetAddress(ccAddr));
		message.setSubject(subject);

		// creates message part
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(mailBody);
		Multipart multipart = new MimeMultipart();

		// adds attachments
		for (EmailAttachmentModel emailAttachmentModel : emailAttachments) {
			addAttachment(multipart, emailAttachmentModel);
		}

		multipart.addBodyPart(messageBodyPart);

		// sets the multi-part as e-mail's content
		message.setContent(multipart);

		// sends the e-mail
		Transport.send(message);

		System.out.println("Email with One attachment sent");

	}

	private void addAttachment(Multipart multipart, EmailAttachmentModel emailAttachmentModel)
			throws MessagingException {
		BodyPart messageBodyPart = new MimeBodyPart();
		DataSource source = new ByteArrayDataSource(emailAttachmentModel.getContent(),
				emailAttachmentModel.getMimeType());
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(emailAttachmentModel.getFilename());
		multipart.addBodyPart(messageBodyPart);

	}

}
