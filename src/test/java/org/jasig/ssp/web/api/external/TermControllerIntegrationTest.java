package org.jasig.ssp.web.api.external; // NOPMD

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.TermTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link TermController} tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class TermControllerIntegrationTest {

	@Autowired
	private transient TermController controller;

	private static final String TERM_NAME = "Fall 2012";

	private static final String TERM_CODE = "FA12";

	/**
	 * Test the {@link TermController#get(String)} action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final TermTO obj = controller.byId(TERM_CODE);

		assertNotNull(
				"Returned TermTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Term.Name did not match.", TERM_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link TermController#get(String)} action returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final TermTO obj = controller.byId("invalid id");

		assertNull(
				"Returned TermTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link TermController#getAll(Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<TermTO> list = controller.getAll(null, null, null,
				null).getRows();

		assertNotNull("List should not have been null.", list);
		assertNotEmpty("List action should have returned some objects.", list);
	}

	/**
	 * Test the {@link TermController#getAll(Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllResults() {
		final Collection<TermTO> list = controller.getAll(null, null, null,
				null).getRows();

		final Iterator<TermTO> iter = list.iterator();

		TermTO term = iter.next();
		assertTrue("Name should have been longer than 0 characters.", term
				.getName().length() > 0);

		term = iter.next();
		assertNotNull("EndDate should not have been null.", term.getEndDate());
	}

	/**
	 * Test the {@link TermController#getAll(Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllLimits() {
		// arrange, act
		final Collection<TermTO> list = controller.getAll(null, null, null,
				null).getRows();
		final Collection<TermTO> filtered = controller.getAll(0, 1, null,
				null).getRows();

		// assert
		assertTrue("List should have returned several data rows.",
				list.size() > 1);

		final Iterator<TermTO> iter = list.iterator();

		TermTO term = iter.next();
		assertTrue("Name should have been longer than 0 characters.", term
				.getName().length() > 0);

		term = iter.next();
		assertNotNull("EndDate should not have been null.", term.getEndDate());

		assertEquals("Limit didn't limit results correctly.", 1,
				filtered.size());
		assertNotEquals(
				"GetAll and filtered lists should have been different sizes.",
				list.size(), filtered.size());
	}

	/**
	 * Test that getLogger() returns the matching log class name for the current
	 * class under test.
	 */
	@Test
	public void testLogger() {
		// arrange, act
		final Logger logger = controller.getLogger();

		// assert
		assertEquals("Log class name did not match.", controller.getClass()
				.getName(), logger.getName());
	}
}