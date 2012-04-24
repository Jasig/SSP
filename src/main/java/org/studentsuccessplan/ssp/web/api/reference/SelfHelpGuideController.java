package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideService;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/selfHelpGuide")
public class SelfHelpGuideController extends
		AbstractAuditableReferenceController<SelfHelpGuide, SelfHelpGuideTO> {

	@Autowired
	protected transient SelfHelpGuideService service;

	@Override
	protected AuditableCrudService<SelfHelpGuide> getService() {
		return service;
	}

	protected SelfHelpGuideController() {
		super(SelfHelpGuide.class, SelfHelpGuideTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SelfHelpGuideController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
