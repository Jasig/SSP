package edu.sinclair.ssp.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;

import org.springframework.beans.factory.annotation.Autowired;

public class VelocityTemplateHelper {
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	public String generateContentFromTemplate(String templateText, HashMap<String, Object> parameters) throws ResourceNotFoundException, ParseErrorException, Exception {

		String templateId = UUID.randomUUID().toString();
		
		StringResourceLoader.getRepository().putStringResource(templateId, templateText);
        Template template = velocityEngine.getTemplate(templateId);
        VelocityContext context = new VelocityContext();

        List<String> params = new ArrayList<String>(parameters.keySet());
        
        for (int i=0; i<params.size(); i++)
        	context.put(params.get(i), parameters.get(params.get(i)));
        
	    StringWriter writer = new StringWriter();
	    template.merge(context, writer);
	    
        return (writer.toString());
	}
}
