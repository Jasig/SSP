package edu.sinclair.ssp.config.logging;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class LogBackConfigLoaderTest {

	private Logger logger = LoggerFactory
			.getLogger(LogBackConfigLoaderTest.class);

	@Test
	public void test() {
		try {
			assertNotNull(new LogBackConfigLoader(
					System.getenv("SSP_TESTCONFIGDIR") + "logback.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		logger.info("Testing logger location");
	}
}
