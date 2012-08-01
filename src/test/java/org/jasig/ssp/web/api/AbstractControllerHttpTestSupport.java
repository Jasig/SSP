package org.jasig.ssp.web.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.transferobject.TransferObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Setup the mock controller, request, and response objects for testing Spring
 * Controller annotation mappings. Replaces the default response handlers with
 * {@link JacksonMethodReturnValueHandler}.
 * 
 * <p>
 * The custom resolve handler is used since Jackson requests are handled by full
 * runtime Spring, but that the built-in Spring mock objects do not support
 * anything but View and String responses.
 * 
 * @author jon.adams
 * 
 * @param <C>
 *            Controller class under test
 * @param <TO>
 *            Transfer object to send and receive from the controller.
 * @param <T>
 *            Model to send and receive via the transfer object class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
public abstract class AbstractControllerHttpTestSupport<C extends AbstractBaseController, TO extends TransferObject<T>, T extends Auditable> { // NOPMD
	// Class needs to be abstract so it won't try to run tests on it

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractControllerHttpTestSupport.class);

	@Autowired
	protected transient ApplicationContext applicationContext;

	/**
	 * Request that is setup in the test method with the sample test data and is
	 * then used in the call to {@link #handlerAdapter} that will fill the
	 * mocked HTTP response with the result of the test on the controller based
	 * on the request data.
	 */
	protected transient MockHttpServletRequest request;

	/**
	 * Response that should be used in the test method's call to
	 * {@link #handlerAdapter} that will fill the mocked HTTP response with the
	 * result of the test on the controller.
	 */
	protected transient MockHttpServletResponse response;

	protected transient RequestMappingHandlerAdapter handlerAdapter;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		handlerAdapter = applicationContext.getBean(
				"sspRequestMappingHandlerAdapter",
				RequestMappingHandlerAdapter.class);

		// set custom handler for SSP return object types since the default
		// handlers provided by Spring don't work for Jackson-serialized objects
		final List<HandlerMethodReturnValueHandler> returnValueHandlers = new
				ArrayList<HandlerMethodReturnValueHandler>(1);
		returnValueHandlers.add(new JacksonMethodReturnValueHandler());
		handlerAdapter.setReturnValueHandlers(returnValueHandlers);
	}

	/**
	 * Once handleAdapter.handle is called, use this helper method to retrieve
	 * the returned object as Jackson would have seen it before serialization.
	 * 
	 * <p>
	 * Example usage:
	 * <code>ChallengeTO result = (ChallengeTO) getFirstModelObject(mav);</code>
	 * 
	 * @param mav
	 *            Model and view response from handleAdapter.handle
	 * @return The model object in the ModelAndView model property.
	 */
	protected Object getModelObject(final ModelAndView mav) {
		assertNotNull("Model and View response should not have been null.", mav);

		final Map<String, Object> m = mav.getModel();
		assertNotNull("Model should not have been null.", m);
		assertFalse("Model should not have been empty.", m.isEmpty());

		return m.get(JacksonMethodReturnValueHandler.KEY);
	}

	/**
	 * This method finds the handler for a given request URI, or throws an
	 * exception if none were found.
	 * 
	 * <p>
	 * It will also ensure that the URI Parameters i.e. /context/test/{name} are
	 * added to the request
	 * 
	 * @param request
	 *            the request for which to find a handler
	 * @return The handler that agreed to handle the specified request.
	 * @throws NoSuchMethodException
	 *             if no acceptable handlers could be found
	 */
	protected Object getHandler(final MockHttpServletRequest request)
			throws NoSuchMethodException {
		HandlerExecutionChain chain = null; // NOPMD by jon.adams on 5/14/12

		final Map<String, HandlerMapping> map = applicationContext
				.getBeansOfType(HandlerMapping.class);
		final Iterator<HandlerMapping> itt = map.values().iterator();

		while (itt.hasNext()) {
			final HandlerMapping mapping = itt.next();

			try {
				chain = mapping.getHandler(request);
			} catch (final HttpRequestMethodNotSupportedException exc) {
				// ignore and try next
				LOGGER.info(
						mapping.getClass().getName()
								+ " handler determined it will not handle the request. Message: "
								+ exc.getMessage(), exc);
			} catch (final Exception exc) {
				throw new RuntimeException(exc); // NOPMD
			}

			if (chain == null) {
				// ignore and try next
				LOGGER.debug(mapping.getClass().getName()
						+ " handler determined it will not handle the request.");
			} else {
				// found one. quit looking for more.
				break;
			}
		}

		if (chain == null) {
			throw new NoSuchMethodException(
					"Unable to find handler for request URI: "
							+ request.getRequestURI());
		}

		return chain.getHandler();
	}
}