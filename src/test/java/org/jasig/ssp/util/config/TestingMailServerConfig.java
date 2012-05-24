/**
 * 
 */
package org.jasig.ssp.util.config;

import com.dumbster.smtp.SimpleSmtpServer;

/**
 * Simple configuration for injecting port information from Spring configuration
 * for the fake mail server to spin up a threaded instance as needed.
 * 
 * @author jon.adams
 * 
 */
public class TestingMailServerConfig {
	private int port;

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(final int port) {
		this.port = port;
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
		return SimpleSmtpServer.start(port);
	}
}