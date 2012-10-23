package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.CampusServiceTOFactory;
import org.jasig.ssp.model.reference.CampusService;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.CampusServiceService;
import org.jasig.ssp.transferobject.reference.CampusServiceTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/campusService")
public class CampusServiceController
		extends
		AbstractAuditableReferenceController<CampusService, CampusServiceTO> {

	@Autowired
	protected transient CampusServiceService service;

	@Override
	protected AuditableCrudService<CampusService> getService() {
		return service;
	}

	@Autowired
	protected transient CampusServiceTOFactory factory;

	@Override
	protected TOFactory<CampusServiceTO, CampusService> getFactory() {
		return factory;
	}

	protected CampusServiceController() {
		super(CampusService.class, CampusServiceTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CampusServiceController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
