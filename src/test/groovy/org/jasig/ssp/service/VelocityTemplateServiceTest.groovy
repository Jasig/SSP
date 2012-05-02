package org.studentsuccessplan.ssp.service
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("service-testConfig.xml")
@TransactionConfiguration
class VelocityTemplateServiceTest {

	@Autowired
	private VelocityTemplateService service;

	@Test
	public void testGenerateContentFromTemplate(){
		assert("Hello Velocity" == service.generateContentFromTemplate('Hello $val', "test-template", ["val":"Velocity"]))

		//template already loaded with this id, so, you could get something a little different from what you expected...
		assert("Hello Velocity" == service.generateContentFromTemplate('Hello $val in the World', "test-template", ["val":"Velocity"]))
	}
}