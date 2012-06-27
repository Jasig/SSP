package org.jasig.ssp.web.api.reference; // NOPMD by jon.adams

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ConfigTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Config controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ConfigControllerIntegrationTest {

	@Autowired
	private transient ConfigController controller;

	private static final UUID CONFIG_ID = UUID
			.fromString("67bd120e-9be1-11e1-ad1f-0026b9e7ff4c");

	private static final String CONFIG_NAME = "app_title";

	/**
	 * Test the {@link ConfigController#get(UUID)} action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final ConfigTO obj = controller.get(CONFIG_ID);

		assertNotNull(
				"Returned ConfigTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Config.Name did not match.", CONFIG_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link ConfigController#get(UUID)} action returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final ConfigTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned ConfigTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link ConfigController#create(ConfigTO)} and
	 * {@link ConfigController#delete(UUID)} actions.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testControllerCreateThrowsInvalidOperation()
			throws ObjectNotFoundException, ValidationException {

		// Create a valid Config
		final ConfigTO obj = new ConfigTO(null, "", "", (short) 5); // NOPMD
		controller.create(obj);
		fail("Config instances can not be created, so an exception should have been thrown.");
	}

	/**
	 * Test the
	 * {@link ConfigController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<ConfigTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}
}