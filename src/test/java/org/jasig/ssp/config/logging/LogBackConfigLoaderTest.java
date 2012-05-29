package org.jasig.ssp.config.logging;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogBackConfigLoaderTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LogBackConfigLoaderTest.class);

	@Test
	public void test() {
		String location = System.getenv("SSP_TESTCONFIGDIR")
				+ System.getProperty("file.separator")
				+ "logback.xml";
		System.out.println("Attempting to load Logback Configuration from " // NOPMD
				+ location);
		try {
			assertNotNull("Failed to load file",
					new LogBackConfigLoader(location));
		} catch (Exception e) {
			LOGGER.error("Failed to load file", e);
			fail("Failed to load file");
		}
	}
}
