package org.studentsuccessplan.ssp.config.logging;

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
		try {
			assertNotNull("Failed to load file", new LogBackConfigLoader(
					System.getenv("SSP_TESTCONFIGDIR") + "logback.xml"));
		} catch (Exception e) {
			LOGGER.error("Failed to load file", e);
			fail("Failed to load file");
		}
	}
}
