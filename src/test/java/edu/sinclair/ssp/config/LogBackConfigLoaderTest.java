package edu.sinclair.ssp.config;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBackConfigLoaderTest {

	private Logger logger = LoggerFactory.getLogger(LogBackConfigLoaderTest.class);
	
	@Test
	public void test() {
		try{
			assertNotNull(new LogBackConfigLoader("/usr/local/etc/ssp/logback.xml"));
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		logger.info("Testing logger location");
	}

}
