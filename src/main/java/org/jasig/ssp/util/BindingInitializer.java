package org.jasig.ssp.util;

import java.util.UUID;

import org.jasig.ssp.util.uuid.UuidPropertyEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * SSP specific WebBindingInitializer. Registers custom Property Editors.
 * 
 * @author daniel
 * 
 */
public class BindingInitializer implements WebBindingInitializer {

	@Override
	public void initBinder(final WebDataBinder binder,
			final WebRequest request) {
		binder.registerCustomEditor(UUID.class, new UuidPropertyEditor());
	}

}
