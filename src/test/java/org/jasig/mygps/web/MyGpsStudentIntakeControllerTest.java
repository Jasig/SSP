package org.jasig.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jasig.mygps.business.StudentIntakeFormManager;
import org.jasig.mygps.model.transferobject.FormTO;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class MyGpsStudentIntakeControllerTest {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(MyGpsStudentIntakeControllerTest.class);

	private transient MyGpsStudentIntakeController controller;

	private transient StudentIntakeFormManager manager;

	@Before
	public void setUp() {
		manager = createMock(StudentIntakeFormManager.class);

		controller = new MyGpsStudentIntakeController();
		controller.setManager(manager);
	}

	@Test
	public void testGetForm() throws ObjectNotFoundException {
		final FormTO form = new FormTO();
		expect(manager.populate()).andReturn(form);
		replay(manager);

		try {
			final FormTO response = controller.getForm();
			verify(manager);
			assertEquals("Expected response was not returned.", form, response);
		} catch (Exception e) {
			fail("controller error");
		}
	}

	@Test
	public void testSaveForm() {
		final FormTO form = new FormTO();
		try {
			expect(manager.save(form)).andReturn(new Person());
		} catch (ObjectNotFoundException e) {
			fail("controller error");
		}

		replay(manager);

		try {
			final boolean response = controller.saveForm(form);
			verify(manager);
			assertTrue("Controller response did not return success.", response);
		} catch (Exception e) {
			fail("controller error");
		}
	}
}
