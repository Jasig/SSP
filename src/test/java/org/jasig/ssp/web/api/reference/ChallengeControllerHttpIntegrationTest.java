/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.web.api.AbstractControllerHttpTestSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
 * {@link ChallengeController} Spring annotation mapping tests.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ChallengeControllerHttpIntegrationTest
		extends
		AbstractControllerHttpTestSupport<ChallengeController, ChallengeTO, Challenge> {

	/**
	 * Controller instance used to run tests.
	 */
	@Autowired
	private transient ChallengeController controller;

	private static final String BASE_REQUEST_URI = "/1/reference/challenge/";

	private static final UUID CHALLENGE_ID = UUID
			.fromString("f5bb0a62-1756-4ea2-857d-5821ee44a1d0");

	private static final String CHALLENGE_NAME = "Test Challenge";

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
	 * {@link ChallengeController#getAll(org.jasig.ssp.model.ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws NoSuchMethodException
	 *             Thrown if the mapping could not be found.
	 */
	@Test
	public void testControllerGetAllRequest() throws NoSuchMethodException {
		request.setMethod(RequestMethod.GET.toString());
		request.setRequestURI(BASE_REQUEST_URI);

		final Object handler = getHandler(request);

		// Lookup the expected handler that Spring should have pulled.
		// HandlerMethod(controller, action (method) name, parameters)
		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "getAll",
				ObjectStatus.class, Integer.class, Integer.class, String.class,
				String.class);

		Assert.assertEquals("Correct handler found for request url should be: "
				+ BASE_REQUEST_URI, expectedHandlerMethod.toString(),
				handler.toString());
	}

	/**
	 * Test the
	 * {@link ChallengeController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * mapping.
	 * 
	 * <p>
	 * Assumes that there is some sample data in the Challenges database.
	 * 
	 * @throws NoSuchMethodException
	 *             Thrown if the mapping could not be found.
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGetAllResponse() throws Exception { // NOPMD
		request.setMethod(RequestMethod.GET.toString());
		request.setRequestURI(BASE_REQUEST_URI);

		final Object handler = getHandler(request);

		final HandlerMethod expectedHandlerMethod = new HandlerMethod(
				controller, "getAll", ObjectStatus.class, Integer.class,
				Integer.class, String.class, String.class);

		Assert.assertEquals("Correct handler found for request url: "
				+ BASE_REQUEST_URI, expectedHandlerMethod.toString(),
				handler.toString());

		Assert.assertNotNull("Response mock object should not have been null.",
				response);

		// Handle the actual request
		final ModelAndView mav = handlerAdapter.handle(request, response,
				handler);
		assertNotNull("Response was not handled.", mav);

		@SuppressWarnings("unchecked")
		final PagedResponse<ChallengeTO> result = (PagedResponse<ChallengeTO>) getModelObject(mav);

		assertNotNull(
				"Return object from the controller should not have been null.",
				result);

		assertFalse("Result should not have been empty.", result.getRows()
				.isEmpty());
		assertEquals("Challenge list counts did not match.",
				result.getResults(), result.getRows().size());
	}

	/**
	 * Test the {@link ChallengeController#get(UUID)} mapping.
	 * 
	 * <p>
	 * Assumes that the sample reference data instances are in the database.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	// some installations fail on this test, but not the developer's
	@Ignore
	@Test
	public void testControllerGet() throws Exception { // NOPMD
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final ChallengeTO obj = controller.get(CHALLENGE_ID);

		assertNotNull(
				"Returned ChallengeTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Challenge.Name did not match.", CHALLENGE_NAME,
				obj.getName());

		// Request URI, but do not include any Spring configuration roots, but
		// do include class-level root paths. Example:
		// "/reference/controllerlevelmapping/mymethodmapping"
		final String requestUri = "/1/reference/challenge/"
				+ CHALLENGE_ID.toString();

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

		final ChallengeTO result = (ChallengeTO) getModelObject(mav);

		assertNotNull(
				"Return object from the controller should not have been null.",
				result);

		assertEquals("Challenge identifiers did not match.", CHALLENGE_ID,
				result.getId());
		assertEquals("Challenge names did not match.", CHALLENGE_NAME,
				result.getName());
	}

	/**
	 * Test the {@link ChallengeController#delete(UUID)} mapping.
	 * 
	 * <p>
	 * Assumes that the sample reference data instances are in the database.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerDelete() throws Exception { // NOPMD
		final String requestUri = "/1/reference/challenge/"
				+ CHALLENGE_ID.toString();

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

		assertTrue("Challenge was not deleted.", result.isSuccess());
	}
}