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
package org.jasig.ssp.service;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Exposes the velocity template engine to the application.
 * <p>
 * Caches Templates by the templateId.
 */
@Service
public class VelocityTemplateService {

	@Autowired
	private transient VelocityEngine velocityEngine;

	/**
	 * 
	 * @param templateText
	 *            - The full text of the Template (from the database)
	 * @param templateId
	 *            - The id of the template (which must be updated every time the
	 *            template changes)
	 * @param parameters
	 *            - variables that are inserted as requested by the template
	 * @return content
	 * @throws ResourceNotFoundException
	 *             If resource was not found
	 * @throws ParseErrorException
	 *             If there was a parsing error
	 * @throws MethodInvocationException
	 *             MethodInvocationException
	 */
	public String generateContentFromTemplate(final String templateText,
			final String templateId,
			final Map<String, Object> parameters)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException {

		// fill the velocity context with the parameters
		final VelocityContext context = new VelocityContext();
		for (final Map.Entry<String, Object> entry : parameters.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}

		// get the template
		final Template template = getTemplate(templateId, templateText);

		// Process the template, and extract string
		final StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}

	/**
	 * Get the template from the Velocity Template Repository. If the Template
	 * is not in the repository, then add it to the repository. Some processing
	 * occurs on the template, so if we can reuse the id, we save ourselves the
	 * additional processing, as well as remove a potential memory leak.
	 */
	private Template getTemplate(final String templateId,
			final String templateText) {
		Template template = null;
		try {
			template = velocityEngine.getTemplate(templateId);
		} catch (final ResourceNotFoundException e) {
			StringResourceLoader.getRepository().putStringResource(templateId,
					templateText);
			template = velocityEngine.getTemplate(templateId);
		}
		return template;
	}
}
