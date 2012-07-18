package org.jasig.ssp.web.api.external; // NOPMD

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.external.ExternalPersonLiteTO;
import org.jasig.ssp.transferobject.external.FacultyCourseTO;
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
 * {@link FacultyCourseController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class FacultyCourseControllerIntegrationTest {

	@Autowired
	private transient FacultyCourseController controller;

	private static final String FACULTY_SCHOOL_ID = "uf928711";

	private static final String TERM_CODE = "FA12";

	private static final String FORMATTED_COURSE = "MTH101";

	private static final String TITLE = "College Algebra";

	private static final String LAST_NAME = "Doe";

	/**
	 * Test the {@link FacultyCourseController#getAllCoursesForFaculty(String)}
	 * action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGetAllCoursesForFaculty()
			throws ObjectNotFoundException, ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final PagedResponse<FacultyCourseTO> list = controller
				.getAllCoursesForFaculty(FACULTY_SCHOOL_ID);
		final FacultyCourseTO obj = list.getRows().iterator().next();

		assertNotNull(
				"Returned FacultyCourseTO from the controller should not have been null.",
				obj);

		assertEquals("Returned FacultyCourse.TermCode did not match.",
				TERM_CODE, obj.getTermCode());
		assertEquals("Returned FacultyCourse.FormattedCourse did not match.",
				FORMATTED_COURSE, obj.getFormattedCourse());
		assertEquals("Returned FacultyCourse.Title did not match.", TITLE,
				obj.getTitle());
	}

	/**
	 * Test that the {@link FacultyCourseController#getRoster(String, String)}
	 * action returns the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final PagedResponse<ExternalPersonLiteTO> obj = controller.getRoster(
				"invalid id", "invalid id");

		assertEquals(
				"Returned FacultyCourseTO from the controller should have been null.",
				0, obj.getResults());
	}

	/**
	 * Test the
	 * {@link FacultyCourseController#getAll(Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<FacultyCourseTO> list = controller.getAll(null, null,
				null,
				null).getRows();

		assertNotNull("List should not have been null.", list);
		assertNotEmpty("List action should have returned some objects.", list);
	}

	/**
	 * Test the
	 * {@link FacultyCourseController#getAll(Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllResults() {
		final Collection<FacultyCourseTO> list = controller.getAll(null, null,
				null, null).getRows();

		final Iterator<FacultyCourseTO> iter = list.iterator();

		FacultyCourseTO facultyCourse = iter.next();
		assertTrue("Title should have been longer than 0 characters.",
				facultyCourse.getTitle().length() > 0);

		facultyCourse = iter.next();
		assertNotNull("TermCode should not have been null.",
				facultyCourse.getTermCode());
	}

	/**
	 * Test the
	 * {@link FacultyCourseController#getAll(Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllLimits() {
		// arrange, act
		final Collection<FacultyCourseTO> list = controller.getAll(null, null,
				null,
				null).getRows();
		final Collection<FacultyCourseTO> filtered = controller.getAll(0, 1,
				null,
				null).getRows();

		// assert
		assertTrue("List should have returned several data rows.",
				list.size() > 1);

		final Iterator<FacultyCourseTO> iter = list.iterator();

		FacultyCourseTO facultyCourse = iter.next();
		assertTrue("Title should have been longer than 0 characters.",
				facultyCourse.getTitle().length() > 0);

		facultyCourse = iter.next();
		assertNotNull("TermCode() should not have been null.",
				facultyCourse.getTermCode());

		assertEquals("Limit didn't limit results correctly.", 1,
				filtered.size());
		assertNotEquals(
				"GetAll and filtered lists should have been different sizes.",
				list.size(), filtered.size());
	}

	@Test
	public void testGetAllCoursesForFaculty() throws ObjectNotFoundException,
			ValidationException {
		// arrange, act
		final PagedResponse<FacultyCourseTO> list = controller
				.getAllCoursesForFaculty(FACULTY_SCHOOL_ID);

		// assert
		assertEquals("Course list should have returned 2 courses.", 2,
				list.getResults());
		assertEquals("Course name did not match.", FORMATTED_COURSE, list
				.getRows().iterator().next().getFormattedCourse());
	}

	@Test
	public void testGetRoster() throws ObjectNotFoundException,
			ValidationException {
		// arrange, act
		final PagedResponse<ExternalPersonLiteTO> list = controller
				.getRoster(FACULTY_SCHOOL_ID, FORMATTED_COURSE);

		// assert
		assertEquals("List should have returned 2 students.", 2,
				list.getResults());
		assertEquals("Last name did not match.", LAST_NAME, list
				.getRows().iterator().next().getLastName());
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