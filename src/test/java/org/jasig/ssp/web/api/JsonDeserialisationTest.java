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
			//TODO Currently ReportTOs are not serializable by Jackson, this is not an issue for reports currently
			if(clazz.getSimpleName().contains("ReportTO"))
				continue;
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