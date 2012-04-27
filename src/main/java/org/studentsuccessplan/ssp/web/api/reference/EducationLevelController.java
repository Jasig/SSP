package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.factory.TOFactory;
import org.studentsuccessplan.ssp.factory.reference.EducationLevelTOFactory;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.EducationLevelService;
import org.studentsuccessplan.ssp.transferobject.reference.EducationLevelTO;

@PreAuthorize("hasRole('ROLE_USER')")
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
