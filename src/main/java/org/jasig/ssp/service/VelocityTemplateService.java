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
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}

		// get the template
		final Template template = getTemplate(templateId, templateText);

		// Process the template, and extract string
		final StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return (writer.toString());
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
		} catch (ResourceNotFoundException e) {
			StringResourceLoader.getRepository().putStringResource(templateId,
					templateText);
			template = velocityEngine.getTemplate(templateId);
		}
		return template;
	}
}
