package edu.sinclair.ssp.web.api.reference;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is just an example of doing an integration test with the controllers
 * 
 * @author daniel
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("ControllerIntegrationTests-context.xml")
public class ChallengeControllerIntegrationTest {

	@Autowired
	private ChallengeController controller;

	@Test
	public void integrationTest() {
		assertNotNull(controller);

		// do stuff here...
	}

}
