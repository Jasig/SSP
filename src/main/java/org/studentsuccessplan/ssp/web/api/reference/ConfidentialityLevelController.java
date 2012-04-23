package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ConfidentialityLevelService;
import org.studentsuccessplan.ssp.transferobject.reference.ConfidentialityLevelTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/confidentialityLevel")
public class ConfidentialityLevelController
		extends
		AbstractAuditableReferenceController<ConfidentialityLevel, ConfidentialityLevelTO> {

	@Autowired
	protected transient ConfidentialityLevelService service;

	@Override
	protected AuditableCrudService<ConfidentialityLevel> getService() {
		return service;
	}

	protected ConfidentialityLevelController() {
		super(ConfidentialityLevel.class, ConfidentialityLevelTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfidentialityLevelController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
