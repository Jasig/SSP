/**
 * 
 */
package org.jasig.ssp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dumbster.smtp.SimpleSmtpServer;

/**
 * Simple configuration for injecting port information from Spring configuration
 * for the fake mail server to spin up a threaded instance as needed.
 * 
 * @author jon.adams
 * 
 */
@Service
public class MailConfig {

	@Value("#{configProperties.send_mail}")
	private transient boolean sendMail;

	@Value("#{configProperties.smtp_port}")
	private transient int port;

	@Value("#{configProperties.enable_mock_mail_server}")
	private transient boolean enableMockMailServer;

	/**
	 * @return sendMail
	 */
	public boolean isSendMail() {
		return sendMail;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Starts up a new instance of the mail server on the port as configured in
	 * Spring. ALWAYS call the stop() method when finished.
	 * 
	 * @return A new server instance bound to the configured port. ALWAYS call
	 *         the stop() method on this instance when finished using for
	 *         testing.
	 */
	public SimpleSmtpServer spawn() {
		if (enableMockMailServer) {
			return SimpleSmtpServer.start(port);
		}

		return null;
	}
}