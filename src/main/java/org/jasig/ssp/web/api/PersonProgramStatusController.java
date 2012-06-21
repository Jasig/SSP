package org.jasig.ssp.web.api; // NOPMD

import org.jasig.ssp.factory.PersonProgramStatusTOFactory;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.transferobject.PersonProgramStatusTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Services to manipulate ProgramStatuss.
 * <p>
 * Mapped to URI path <code>/1/person/{personId}/programStatus</code>
 */
@Controller
@RequestMapping("/1/person/{personId}/programStatus")
public class PersonProgramStatusController
		extends
		AbstractPersonAssocController<PersonProgramStatus, PersonProgramStatusTO> {

	protected PersonProgramStatusController() {
		super(PersonProgramStatus.class, PersonProgramStatusTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonProgramStatusController.class);

	@Autowired
	private transient PersonProgramStatusService service;

	@Autowired
	private transient PersonProgramStatusTOFactory factory;

	@Override
	protected PersonProgramStatusTOFactory getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected PersonProgramStatusService getService() {
		return service;
	}

	@Override
	public String permissionBaseName() {
		return "PROGRAM_STATUS";
	}
}