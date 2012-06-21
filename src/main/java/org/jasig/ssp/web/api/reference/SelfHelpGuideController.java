package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/selfHelpGuide")
public class SelfHelpGuideController
		extends
		AbstractAuditableReferenceController<SelfHelpGuide, SelfHelpGuideTO> {

	@Autowired
	protected transient SelfHelpGuideService service;

	@Override
	protected AuditableCrudService<SelfHelpGuide> getService() {
		return service;
	}

	@Autowired
	protected transient SelfHelpGuideTOFactory factory;

	@Override
	protected TOFactory<SelfHelpGuideTO, SelfHelpGuide> getFactory() {
		return factory;
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
