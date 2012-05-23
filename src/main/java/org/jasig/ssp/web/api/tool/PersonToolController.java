package org.jasig.ssp.web.api.tool;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.tool.PersonToolTOFactory;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.service.tool.PersonToolService;
import org.jasig.ssp.transferobject.tool.PersonToolTO;
import org.jasig.ssp.web.api.AbstractPersonAssocController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/person/{personId}/tool")
public class PersonToolController
		extends AbstractPersonAssocController<PersonTool, PersonToolTO> {

	protected PersonToolController() {
		super(PersonTool.class, PersonToolTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonToolController.class);

	@Autowired
	protected transient PersonToolService service;

	@Autowired
	protected transient PersonToolTOFactory factory;

	@Override
	protected PersonToolService getService() {
		return service;
	}

	@Override
	protected TOFactory<PersonToolTO, PersonTool> getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
