package org.studentsuccessplan.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studentsuccessplan.mygps.business.StudentIntakeFormManager;
import org.studentsuccessplan.mygps.model.transferobject.FormTO;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public class MyGpsStudentIntakeControllerTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsStudentIntakeControllerTest.class);

	private MyGpsStudentIntakeController controller;
	private StudentIntakeFormManager manager;

	@Before
	public void setup() {
		manager = createMock(StudentIntakeFormManager.class);

		controller = new MyGpsStudentIntakeController();
		controller.setManager(manager);
	}

	@Test
	public void testGetForm() {
		FormTO form = new FormTO();
		expect(manager.populate()).andReturn(form);
		replay(manager);

		try {
			FormTO response = controller.getForm();
			verify(manager);
			assertEquals(form, response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void testSaveForm() {
		FormTO form = new FormTO();
		try {
			manager.save(form);
		} catch (ObjectNotFoundException e) {
			LOGGER.error("mock object error", e);
		}
		replay(manager);

		try {
			boolean response = controller.saveForm(form);
			verify(manager);
			assertTrue(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}
}
