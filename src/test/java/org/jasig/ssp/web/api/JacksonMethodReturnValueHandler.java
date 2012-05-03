package org.jasig.ssp.web.api;

import java.util.List;
import java.util.Set;

import org.jasig.ssp.transferobject.AuditableTO;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.TransferObject;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Handles return values of types used in the SSP API, and interpreting them as
 * objects that Jackson will serialize.
 * 
 * <p>
 * Handles return types from controllers used in the SSP API like
 * {@link AbstractReferenceTO}, {@link AuditableTO}, {@link PagingTO}, and Lists
 * and Sets with values of those types.
 * <p>
 * This custom resolver is required because the built-in Spring mocks only
 * handle Views and simple String return types, so this handler is needed to
 * respond with an affirmative that a handler exists for these return types, and
 * to fill the response with the return value from the Controller for testing.
 * <p>
 * Fills the ModelAndView response Model property with an unnamed instance of
 * whatever was returned by calling the handler. Example retrieval:
 * <p>
 * <code>modelAndViewHandlerReturnValue.getModel().values().iterator().next()</code>
 * 
 * @author jon.adams
 */
public class JacksonMethodReturnValueHandler implements
		HandlerMethodReturnValueHandler {

	public final static String KEY = "JacksonMethodReturnValueHandler_ObjectKey";

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		Class<?> paramType = returnType.getParameterType();
		return (AbstractReferenceTO.class
				.equals(paramType) || AuditableTO.class.equals(paramType)
				|| TransferObject.class
						.equals(paramType) || PagingTO.class
					.equals(paramType));
	}

	@Override
	public void handleReturnValue(
			Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws Exception {

		if (returnValue == null) {
			return;
		} else if (returnValue instanceof TransferObject<?> ||
				returnValue instanceof PagingTO<?, ?> ||
				returnValue instanceof List<?> ||
				returnValue instanceof Set<?>) {
			mavContainer.getModel().addAttribute(returnValue);
		} else {
			// should not happen — check that supportsReturnType() matches any
			// checks here in handleReturnValue().
			throw new UnsupportedOperationException("Unexpected return type: " +
					returnType.getParameterType().getName() + " in method: "
					+ returnType.getMethod());
		}
	}
}
