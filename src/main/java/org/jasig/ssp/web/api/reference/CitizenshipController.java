package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.CitizenshipTOFactory;
import org.jasig.ssp.model.reference.Citizenship;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.CitizenshipService;
import org.jasig.ssp.transferobject.reference.CitizenshipTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/citizenship")
public class CitizenshipController
		extends
		AbstractAuditableReferenceController<Citizenship, CitizenshipTO> {

	@Autowired
	protected transient CitizenshipService service;

	@Override
	protected AuditableCrudService<Citizenship> getService() {
		return service;
	}

	@Autowired
	protected transient CitizenshipTOFactory factory;

	@Override
	protected TOFactory<CitizenshipTO, Citizenship> getFactory() {
		return factory;
	}

	protected CitizenshipController() {
		super(Citizenship.class, CitizenshipTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CitizenshipController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
