package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.PersonalityTypeTOFactory;
import org.jasig.ssp.model.reference.PersonalityType;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.PersonalityTypeService;
import org.jasig.ssp.transferobject.reference.PersonalityTypeTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/personalityType")
public class PersonalityTypeController
		extends
		AbstractAuditableReferenceController<PersonalityType, PersonalityTypeTO> {

	@Autowired
	protected transient PersonalityTypeService service;

	@Override
	protected AuditableCrudService<PersonalityType> getService() {
		return service;
	}

	@Autowired
	protected transient PersonalityTypeTOFactory factory;

	@Override
	protected TOFactory<PersonalityTypeTO, PersonalityType> getFactory() {
		return factory;
	}

	protected PersonalityTypeController() {
		super(PersonalityType.class, PersonalityTypeTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonalityTypeController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
