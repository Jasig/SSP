/**
 * 
 */
package org.jasig.ssp.config;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.internet.MimeMessage;

import org.jasig.ssp.service.reference.ConfigException;
import org.jasig.ssp.service.reference.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.dumbster.smtp.SimpleSmtpServer;

/**
 * Mock Mail Service for use with tests - Starts a Mock SMTP server on the port
 * given in the Config table
 */
@Service
public class MockMailService implements JavaMailSender {

	@Autowired
	private transient ConfigService configService;

	private transient JavaMailSenderImpl internal;

	private transient SimpleSmtpServer smtpServer;

	@PostConstruct
	public void startup() {
		internal = new JavaMailSenderImpl();
		internal.setPort(getPort());

		final Properties props = new Properties();
		props.setProperty("mail.smtp.timeout", "5");
		internal.setJavaMailProperties(props);
	}

	/**
	 * Shuts down the Service when Spring Shuts Down.
	 */
	@PreDestroy
	public void close() {
		if ((smtpServer != null) && !smtpServer.isStopped()) {
			smtpServer.stop();
		}
	}

	private int getPort() {
		return configService
				.getByNameExceptionOrDefaultAsInt("test_env_mock_mail_server_port");
	}

	/**
	 * To use the MockSmtpServer for a test, you will need to call this method
	 * at the start of every test. That will reinitialize the server so that you
	 * can use its verification methods.
	 * 
	 * @return An instance of the mock SMTP server for testing.
	 */
	public SimpleSmtpServer getSmtpServer() {

		if ((smtpServer == null)) {
			smtpServer = SimpleSmtpServer.start(getPort());
		} else if (!smtpServer.isStopped()) {
			smtpServer.stop();
			smtpServer = SimpleSmtpServer.start(getPort());
		}

		if (smtpServer.isStopped()) {
			throw new ConfigException("smtpServer",
					"Simple SmtpServer unable to startup");
		}

		return smtpServer;
	}

	//
	// EVERYTHING following is for delegation to the JavaMailSenderImpl
	//

	@Override
	public void send(final SimpleMailMessage simpleMessage)
			throws MailException {
		internal.send(simpleMessage);
	}

	@Override
	public void send(final SimpleMailMessage[] simpleMessages)
			throws MailException {
		internal.send(simpleMessages);
	}

	@Override
	public MimeMessage createMimeMessage() {
		return internal.createMimeMessage();
	}

	@Override
	public MimeMessage createMimeMessage(final InputStream contentStream)
			throws MailException {
		return internal.createMimeMessage(contentStream);
	}

	@Override
	public void send(final MimeMessage mimeMessage) throws MailException {
		internal.send(mimeMessage);
	}

	@Override
	public void send(final MimeMessage[] mimeMessages) throws MailException {
		internal.send(mimeMessages);
	}

	@Override
	public void send(final MimeMessagePreparator mimeMessagePreparator)
			throws MailException {
		internal.send(mimeMessagePreparator);
	}

	@Override
	public void send(final MimeMessagePreparator[] mimeMessagePreparators)
			throws MailException {
		internal.send(mimeMessagePreparators);
	}
}