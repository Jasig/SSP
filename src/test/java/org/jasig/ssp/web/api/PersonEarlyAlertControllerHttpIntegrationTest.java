package org.jasig.ssp.web.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@link PersonEarlyAlertController} Spring annotation mapping tests.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class PersonEarlyAlertControllerHttpIntegrationTest
		extends
		AbstractControllerHttpTestSupport<PersonEarlyAlertController, EarlyAlertTO, EarlyAlert> {

	/**
	 * Controller instance used to run tests.
	 */
	@Autowired
	private transient PersonEarlyAlertController controller;

	private static final UUID PERSON_ID = UUID
			.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea");

	private static final String PERSON_STUDENTID = "student0";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Override
	@Before
	public void setUp() {
		super.setUp();
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the
	 * {@link PersonEarlyAlertController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the test throws any unexpected exceptions.
	 */
	@Test
	public void testControllerGetAllRequest() throws Exception {
		final String requestUri = "/1/person/" + PERSON_ID + "/earlyAlert/";

		request.setMethod(RequestMethod.GET.toString());
		request.setRequestURI(requestUri);

		final Object handler = getHandler(request);

		// Lookup the expected handler that Spring should have pulled.
		// HandlerMethod(controller, action (method) name, parameters)
		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "getAll",
				UUID.class, ObjectStatus.class, Integer.class, Integer.class,
				String.class,
				String.class);

		Assert.assertEquals("Correct handler found for request url: "
				+ requestUri, expectedHandlerMethod.toString(),
				handler.toString());
	}

	/**
	 * Test the
	 * {@link PersonEarlyAlertController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * mapping.
	 * 
	 * <p>
	 * Assumes that there is no sample data in the EarlyAlert database for the
	 * usual sample test person.
	 * 
	 * @throws Exception
	 *             Thrown if the test throws any unexpected exceptions.
	 */
	@Test
	public void testControllerGetAllResponse() throws Exception {
		final String requestUri = "/1/person/" + PERSON_ID + "/earlyAlert/";
		request.setMethod(RequestMethod.GET.toString());
		request.setRequestURI(requestUri);

		final Object handler = getHandler(request);

		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "getAll", UUID.class, ObjectStatus.class,
				Integer.class,
				Integer.class, String.class, String.class);

		Assert.assertEquals("Correct handler found for request url: "
				+ requestUri, expectedHandlerMethod.toString(),
				handler.toString());

		Assert.assertNotNull("Response mock object should not have been null.",
				response);

		// Handle the actual request
		final ModelAndView mav = handlerAdapter.handle(request, response,
				handler);
		assertNotNull("Response was not handled.", mav);

		@SuppressWarnings("unchecked")
		final PagingTO<EarlyAlertTO, Person> result = (PagingTO<EarlyAlertTO, Person>) getModelObject(mav);

		assertNotNull(
				"Return object from the controller should not have been null.",
				result);

		assertTrue("Result should have been empty.", result.getRows()
				.isEmpty());
		assertEquals("Person list counts did not match.",
				result.getResults(), result.getRows().size());
	}

	/**
	 * Test the {@link PersonEarlyAlertController#delete(UUID, UUID)} mapping.
	 * 
	 * <p>
	 * Assumes that the sample data instances are in the database.
	 * 
	 * @throws Exception
	 *             Thrown if the test throws any unexpected exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerDeleteInvalidId() throws Exception {
		final String requestUri = "/1/person/" + PERSON_ID + "/earlyAlert/"
				+ UUID.randomUUID().toString();

		request.setMethod(RequestMethod.DELETE.toString());
		request.setRequestURI(requestUri);

		final Object handler = getHandler(request);

		// Lookup the expected handler that Spring should have pulled.
		// HandlerMethod(controller, action (method) name, parameters)
		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "delete", UUID.class, UUID.class);

		Assert.assertEquals("Correct handler found for request url: "
				+ requestUri, expectedHandlerMethod.toString(),
				handler.toString());
		Assert.assertNotNull("Response mock object should not have been null.",
				response);

		// Handle the actual request
		final ModelAndView mav = handlerAdapter.handle(request, response,
				handler);
		assertNotNull("Response was not handled.", mav);

		final ServiceResponse result = (ServiceResponse) getModelObject(mav);

		assertNotNull(
				"Return object from the controller should not have been null.",
				result);

		assertFalse("Invalid person ID did not return a failure message.",
				result.isSuccess());
	}

	/**
	 * Test the {@link PersonEarlyAlertController#create(String, EarlyAlertTO)}
	 * mapping.
	 * 
	 * @throws Exception
	 *             Thrown if the test throws any unexpected exceptions.
	 */
	@Test
	public void testControllerCreateWithStudentId() throws Exception {
		final String requestUri = "/1/person/earlyAlert/";

		request.setMethod(RequestMethod.POST.toString());
		request.setRequestURI(requestUri);
		request.setQueryString("?studentId=" + PERSON_STUDENTID);

		final Object handler = getHandler(request);

		// Lookup the expected handler that Spring should have pulled.
		// HandlerMethod(controller, action (method) name, parameters)
		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "create",
				String.class, EarlyAlertTO.class);

		Assert.assertEquals("Correct handler found for request url: "
				+ requestUri, expectedHandlerMethod.toString(),
				handler.toString());
	}

	/**
	 * Test the {@link PersonEarlyAlertController#create(String, EarlyAlertTO)}
	 * mapping.
	 * 
	 * @throws Exception
	 *             Thrown if the test throws any unexpected exceptions.
	 */
	@Test
	public void testControllerCreateWithPersonId() throws Exception {
		final String requestUri = "/1/person/" + PERSON_ID + "/earlyAlert/";

		request.setMethod(RequestMethod.POST.toString());
		request.setRequestURI(requestUri);

		final Object handler = getHandler(request);

		// Lookup the expected handler that Spring should have pulled.
		// HandlerMethod(controller, action (method) name, parameters)
		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "create",
				UUID.class, EarlyAlertTO.class);

		Assert.assertEquals("Correct handler found for request url: "
				+ requestUri, expectedHandlerMethod.toString(),
				handler.toString());
	}
}
