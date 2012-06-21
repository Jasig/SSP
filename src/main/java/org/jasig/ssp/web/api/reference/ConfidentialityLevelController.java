package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ConfidentialityLevelTOFactory;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@Autowired
	protected transient ConfidentialityLevelTOFactory factory;

	@Override
	protected TOFactory<ConfidentialityLevelTO, ConfidentialityLevel> getFactory() {
		return factory;
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
