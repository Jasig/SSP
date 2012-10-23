package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.DisabilityTypeTOFactory;
import org.jasig.ssp.model.reference.DisabilityType;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.DisabilityTypeService;
import org.jasig.ssp.transferobject.reference.DisabilityTypeTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/disabilityType")
public class DisabilityTypeController
		extends
		AbstractAuditableReferenceController<DisabilityType, DisabilityTypeTO> {

	@Autowired
	protected transient DisabilityTypeService service;

	@Override
	protected AuditableCrudService<DisabilityType> getService() {
		return service;
	}

	@Autowired
	protected transient DisabilityTypeTOFactory factory;

	@Override
	protected TOFactory<DisabilityTypeTO, DisabilityType> getFactory() {
		return factory;
	}

	protected DisabilityTypeController() {
		super(DisabilityType.class, DisabilityTypeTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DisabilityTypeController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
