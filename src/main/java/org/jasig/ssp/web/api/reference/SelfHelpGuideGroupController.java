package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideGroupTOFactory;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.SelfHelpGuideGroupService;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideGroupTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/selfHelpGuideGroup")
public class SelfHelpGuideGroupController
		extends
		AbstractAuditableReferenceController<SelfHelpGuideGroup, SelfHelpGuideGroupTO> {

	@Autowired
	protected transient SelfHelpGuideGroupService service;

	@Override
	protected AuditableCrudService<SelfHelpGuideGroup> getService() {
		return service;
	}

	@Autowired
	protected transient SelfHelpGuideGroupTOFactory factory;

	@Override
	protected TOFactory<SelfHelpGuideGroupTO, SelfHelpGuideGroup> getFactory() {
		return factory;
	}

	protected SelfHelpGuideGroupController() {
		super(SelfHelpGuideGroup.class, SelfHelpGuideGroupTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SelfHelpGuideGroupController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
