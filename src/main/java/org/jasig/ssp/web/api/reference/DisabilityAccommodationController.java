package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.DisabilityAccommodationTOFactory;
import org.jasig.ssp.model.reference.DisabilityAccommodation;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.DisabilityAccommodationService;
import org.jasig.ssp.transferobject.reference.DisabilityAccommodationTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/disabilityAccommodation")
public class DisabilityAccommodationController
		extends
		AbstractAuditableReferenceController<DisabilityAccommodation, DisabilityAccommodationTO> {

	@Autowired
	protected transient DisabilityAccommodationService service;

	@Override
	protected AuditableCrudService<DisabilityAccommodation> getService() {
		return service;
	}

	@Autowired
	protected transient DisabilityAccommodationTOFactory factory;

	@Override
	protected TOFactory<DisabilityAccommodationTO, DisabilityAccommodation> getFactory() {
		return factory;
	}

	protected DisabilityAccommodationController() {
		super(DisabilityAccommodation.class, DisabilityAccommodationTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DisabilityAccommodationController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
