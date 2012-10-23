package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.DisabilityStatusTOFactory;
import org.jasig.ssp.model.reference.DisabilityStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.DisabilityStatusService;
import org.jasig.ssp.transferobject.reference.DisabilityStatusTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/disabilityStatus")
public class DisabilityStatusController
		extends
		AbstractAuditableReferenceController<DisabilityStatus, DisabilityStatusTO> {

	@Autowired
	protected transient DisabilityStatusService service;

	@Override
	protected AuditableCrudService<DisabilityStatus> getService() {
		return service;
	}

	@Autowired
	protected transient DisabilityStatusTOFactory factory;

	@Override
	protected TOFactory<DisabilityStatusTO, DisabilityStatus> getFactory() {
		return factory;
	}

	protected DisabilityStatusController() {
		super(DisabilityStatus.class, DisabilityStatusTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DisabilityStatusController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
