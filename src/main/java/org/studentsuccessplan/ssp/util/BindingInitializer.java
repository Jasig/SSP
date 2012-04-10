package org.studentsuccessplan.ssp.util;

import java.util.UUID;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import org.studentsuccessplan.ssp.util.uuid.UuidPropertyEditor;

/**
 * SSP specific WebBindingInitializer. Registers custom Property Editors.
 * 
 * @author daniel
 * 
 */
public class BindingInitializer implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(UUID.class, new UuidPropertyEditor());
	}

}
