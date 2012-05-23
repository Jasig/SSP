/**
 * 
 */
package org.jasig.ssp.service.impl;

import static org.junit.Assert.assertTrue;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jon.adams
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration()
@Transactional
public class MessageServiceTest {

	@Autowired
	private transient MessageService service;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Ensures mail testing config value is available and enabled, via
	 * {@link org.jasig.ssp.service.impl.MessageServiceImpl#shouldSendMail()}
	 */
	@Test
	public void testShouldSendMailIsEnabled() {
		assertTrue(
				"Send mail functionaility for testing not enabled in your database configuration settings for \"send_mail\". This should always be enabled in the testing environment.",
				service.shouldSendMail());
	}
}