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

	private Properties config;

	@Before
	public void before() throws IOException {
		config = new Properties();

		Resource file = new ClassPathResource("testConfig.properties");
		config.load(file.getInputStream());
	}

	@Test
	public void test() {
		try {
			assertNotNull(new LogBackConfigLoader(
					config.getProperty("testConfigDirectory") + "logback.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		logger.info("Testing logger location");
	}
}
