package org.jasig.ssp.web.api;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jasig.ssp.util.ClassDiscovery;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.google.common.collect.Lists;

/**
 * Test that all the transfer objects are serializable by the JSON serializer.
 * <p>
 * The sample code for this test was provided by the code at the blog post
 * {@link "http://blog.cuttleworks.com/2011/12/http-media-type-not-supported-exception/"}
 * 
 * @author jon.adams
 */
public class JsonDeserialisationTest {

	/**
	 * Test that all the SSP transfer objects can be serialized.
	 */
	@Test
	public void sspClassesUsedByOurControllersShouldBeDeserialisableByJackson() {
		@SuppressWarnings("rawtypes")
		final List<Class> classes = Lists.newArrayList();
		classes.addAll(ClassDiscovery
				.getClasses("org.jasig.mygps.model.transferobject"));
		classes.addAll(ClassDiscovery
				.getClasses("org.jasig.ssp.transferobject"));

		assertTrue("Not discovering classes for JsonSerializationTest",
				classes.size() > 10);

		for (final Class<?> clazz : classes) {
			assertCanBeMapped(clazz);
		}
	}

	private void assertCanBeMapped(final Class<?> classToTest) {
		final MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		assertTrue(
				classToTest.getSimpleName()
						+ " is not deserialisable, check the swallowed exception in org.codehaus.jackson.map.deser.StdDeserializerProvider.hasValueDeserializerFor",
				converter.canRead(classToTest, MediaType.APPLICATION_JSON));
	}
}