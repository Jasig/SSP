package org.studentsuccessplan.ssp.web.api;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 
 * @author jon.adams
 * 
 * @param <C>
 *            Controller class under test
 * @param <TO>
 *            Transfer object to send and receive from the controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
public abstract class AbstractControllerHttpTestSupport<C extends RestController<TO>, TO> {
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

	// protected transient RequestMappingHandlerAdapter handlerAdapter;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		// handlerAdapter =
		// applicationContext.getBean("sspRequestMappingHandlerAdapter",
		// RequestMappingHandlerAdapter.class);

		// set returnValues since default ModelAndView isn't used in SSP
		// TODO Figure out and then set the correct type of correct handlers
		/*
		 * List<HandlerMethodReturnValueHandler> returnValueHandlers = new
		 * ArrayList<HandlerMethodReturnValueHandler>(1);
		 * returnValueHandlers.add(new HandlerMethodReturnValueHandler());
		 * handlerAdapter.setCustomReturnValueHandlers(returnValueHandlers);
		 */
	}

	/**
	 * This method finds the handler for a given request URI.
	 * 
	 * It will also ensure that the URI Parameters i.e. /context/test/{name} are
	 * added to the request
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Object getHandler(final MockHttpServletRequest request)
			throws Exception {
		HandlerExecutionChain chain = null;

		final Map<String, HandlerMapping> map = applicationContext
				.getBeansOfType(HandlerMapping.class);
		final Iterator<HandlerMapping> itt = map.values().iterator();

		while (itt.hasNext()) {
			final HandlerMapping mapping = itt.next();
			chain = mapping.getHandler(request);
			if (chain != null) {
				break;
			}
		}

		if (chain == null) {
			throw new InvalidParameterException(
					"Unable to find handler for request URI: "
							+ request.getRequestURI());
		}

		return chain.getHandler();
	}
}
