package org.jasig.ssp.transferobject

import org.junit.Test

import static org.junit.Assert.assertEquals
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.core.MethodParameter
import java.lang.reflect.Method
import org.springframework.validation.BindingResult
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.ObjectError
import org.jasig.ssp.dao.ObjectExistsException

/**
 * Behavior of ServiceResponse is very simple, but we changed the JSON
 * serialization mechanism for SSP-480 to add special handling of
 * {@link org.jasig.ssp.dao.ObjectExistsException}, so a test was needed to
 * guard against regressions.
 */
class ServiceResponseTest {

	@Test
	void jsonFromToStringWithDefaultConstructor() {
		def sr = new ServiceResponse()
		assertEquals("{\"success\":\"false\",\"message\":\"\"}", sr.toString())
	}

	@Test
	void jsonFromToStringWithBooleanConstructor() {
		def sr = new ServiceResponse(true)
		assertEquals("{\"success\":\"true\",\"message\":\"\"}", sr.toString())
	}

	@Test
	void jsonFromToStringWithBooleanAndMessageConstructor() {
		def sr = new ServiceResponse(true, "message foo")
		assertEquals("{\"success\":\"true\",\"message\":\"message foo\"}", sr.toString())
	}

	@Test
	void jsonFromToStringWithBooleanAndMethodArgumentNotValidExceptionConstructor() {
		def sb = new StringBuilder()
		def method = StringBuilder.class.getMethod("setLength", Integer.TYPE)
		def errorResult = new BeanPropertyBindingResult(sb, "sb")
		errorResult.addError(new ObjectError("sp", "Woops"))
		def e = new MethodArgumentNotValidException(new MethodParameter(method, 0),
				errorResult)
		def sr = new ServiceResponse(false, e)
		assertEquals("{\"success\":\"false\",\"message\":\"Validation failed for argument null, with 1 error: [ Woops] \"}",
				sr.toString())
	}

	@Test
	void jsonFromToStringWithBooleanAndObjectExistsExceptionConstructor() {
		def lookupFields = ["foo":"bar", "baz":"bap"]
		def e = new ObjectExistsException(String.class.getName(), lookupFields)
		def sr = new ServiceResponse(false, e)
		assertEquals("{\"success\":\"false\",\"message\":\"Found existing {java.lang.String}. Lookup fields {foo: bar} {baz: bap}.\",\"detail\":{\"lookupFields\":{\"baz\":\"bap\",\"foo\":\"bar\"},\"typeInfo\":{\"name\":\"java.lang.String\"}}}", sr.toString())
	}

	@Test
	void jsonFromToStringWithNoLookupParamsNorNameInObjectExistsException() {
		def e = new ObjectExistsException("foo")
		def sr = new ServiceResponse(false, e)
		assertEquals("{\"success\":\"false\",\"message\":\"foo\",\"detail\":{\"lookupFields\":null,\"typeInfo\":{\"name\":null}}}", sr.toString())
	}
}
