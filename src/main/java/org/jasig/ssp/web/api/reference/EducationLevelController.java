package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.EducationLevelTOFactory;
import org.jasig.ssp.model.reference.EducationLevel;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.EducationLevelService;
import org.jasig.ssp.transferobject.reference.EducationLevelTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/educationLevel")
public class EducationLevelController
		extends
		AbstractAuditableReferenceController<EducationLevel, EducationLevelTO> {

	@Autowired
	protected transient EducationLevelService service;

	@Override
	protected AuditableCrudService<EducationLevel> getService() {
		return service;
	}

	@Autowired
	protected transient EducationLevelTOFactory factory;

	@Override
	protected TOFactory<EducationLevelTO, EducationLevel> getFactory() {
		return factory;
	}

	protected EducationLevelController() {
		super(EducationLevel.class, EducationLevelTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EducationLevelController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
