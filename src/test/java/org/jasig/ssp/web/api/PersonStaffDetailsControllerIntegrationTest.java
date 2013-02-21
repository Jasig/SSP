package org.jasig.ssp.web.api;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.hibernate.SessionFactory;
import java.util.List;
import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link PersonStaffDetailsController} tests
 * 
 * @author james.stanley
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class PersonStaffDetailsControllerIntegrationTest {

	@Autowired
	private transient PersonStaffDetailsController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				"ROLE_REFERENCE_READ");
	}

	/**
	 * Test the email action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testGetHomeDepartments() throws ObjectNotFoundException{
		List<String> homeDepartments = controller.getAllHomeDepartments(ObjectStatus.ACTIVE);
		assertEquals("Incorrect number of home departments", homeDepartments.size(), 1);
		assertEquals("Wrong home department", homeDepartments.get(0), "Mathematics");
	}
}
