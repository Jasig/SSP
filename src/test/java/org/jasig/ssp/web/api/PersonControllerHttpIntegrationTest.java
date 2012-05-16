package org.jasig.ssp.web.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.PersonTO;
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
 * {@link PersonController} Spring annotation mapping tests.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class PersonControllerHttpIntegrationTest
		extends
		AbstractControllerHttpTestSupport<PersonController, PersonTO, Person> {

	/**
	 * Controller instance used to run tests.
	 */
	@Autowired
	private transient PersonController controller;

	private static final UUID PERSON_ID = UUID
			.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea");

	private static final String PERSON_FIRSTNAME = "Kenneth";

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
	 * {@link PersonController#getAll(org.jasig.ssp.model.ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGetAllRequest() throws Exception { // NOPMD
		// Request URI, but do not include any Spring configuration roots, but
		// do include class-level root paths. Example:
		// "/controllerlevelmapping/mymethodmapping"
		final String requestUri = "/1/person/"; // NOPMD

		request.setMethod(RequestMethod.GET.toString());
		request.setRequestURI(requestUri);

		final Object handler = getHandler(request);

		// Lookup the expected handler that Spring should have pulled.
		// HandlerMethod(controller, action (method) name, parameters)
		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "getAll",
				ObjectStatus.class, Integer.class, Integer.class, String.class,
				String.class);

		Assert.assertEquals(
				"Correct handler found for request url should have been "
						+ requestUri, expectedHandlerMethod.toString(),
				handler.toString());
	}

	/**
	 * Test the
	 * {@link PersonController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * mapping.
	 * 
	 * <p>
	 * Assumes that there is some sample data in the Persons database.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGetAllResponse() throws Exception { // NOPMD
		final String requestUri = "/1/person/"; // NOPMD
		request.setMethod(RequestMethod.GET.toString());
		request.setRequestURI(requestUri);

		final Object handler = getHandler(request);

		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "getAll", ObjectStatus.class, Integer.class,
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
		final PagingTO<PersonTO, Person> result = (PagingTO<PersonTO, Person>) getModelObject(mav);

		assertNotNull(
				"Return object from the controller should not have been null.",
				result);

		assertFalse("Result should not have been empty.", result.getRows()
				.isEmpty());
		assertEquals("Person list counts did not match.",
				result.getResults(), result.getRows().size());
	}

	/**
	 * Test the {@link PersonController#get(UUID)} mapping.
	 * 
	 * <p>
	 * Assumes that the sample data instances are in the database.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception { // NOPMD
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final PersonTO obj = controller.get(PERSON_ID);

		assertNotNull(
				"Returned PersonTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Person.Name did not match.", PERSON_FIRSTNAME,
				obj.getFirstName());

		// Request URI, but do not include any Spring configuration roots, but
		// do include class-level root paths. Example:
		// "/controllerlevelmapping/mymethodmapping"
		final String requestUri = "/1/person/"
				+ PERSON_ID.toString();

		request.setMethod(RequestMethod.GET.toString());
		request.setRequestURI(requestUri);

		final Object handler = getHandler(request);

		// Lookup the expected handler that Spring should have pulled.
		// HandlerMethod(controller, action (method) name, parameters)
		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "get", UUID.class);

		// For the most part we will be expecting HandlerMethod objects to be
		// returned for our controllers.
		// Calling the to string method will print the complete method
		// signature.
		Assert.assertEquals("Correct handler found for request url: "
				+ requestUri, expectedHandlerMethod.toString(),
				handler.toString());

		Assert.assertNotNull("Response mock object should not have been null.",
				response);

		// Handle the actual request
		final ModelAndView mav = handlerAdapter.handle(request, response,
				handler);
		assertNotNull("Response was not handled.", mav);

		final PersonTO result = (PersonTO) getModelObject(mav);

		assertNotNull(
				"Return object from the controller should not have been null.",
				result);

		assertEquals("Person identifiers did not match.", PERSON_ID,
				result.getId());
		assertEquals("Person names did not match.", PERSON_FIRSTNAME,
				result.getFirstName());
	}

	/**
	 * Test the {@link PersonController#delete(UUID)} mapping.
	 * 
	 * <p>
	 * Assumes that the sample data instances are in the database.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerDelete() throws Exception { // NOPMD
		final String requestUri = "/1/person/"
				+ PERSON_ID.toString();

		request.setMethod(RequestMethod.DELETE.toString());
		request.setRequestURI(requestUri);

		final Object handler = getHandler(request);

		// Lookup the expected handler that Spring should have pulled.
		// HandlerMethod(controller, action (method) name, parameters)
		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "delete",
				UUID.class);

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

		assertTrue("Person was not deleted.", result.isSuccess());
	}
}
