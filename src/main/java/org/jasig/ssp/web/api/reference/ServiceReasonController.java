package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ServiceReasonTOFactory;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.transferobject.reference.ServiceReasonTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/serviceReason")
public class ServiceReasonController
		extends
		AbstractAuditableReferenceController<ServiceReason, ServiceReasonTO> {

	@Autowired
	protected transient ServiceReasonService service;

	@Override
	protected AuditableCrudService<ServiceReason> getService() {
		return service;
	}

	@Autowired
	protected transient ServiceReasonTOFactory factory;

	@Override
	protected TOFactory<ServiceReasonTO, ServiceReason> getFactory() {
		return factory;
	}

	protected ServiceReasonController() {
		super(ServiceReason.class, ServiceReasonTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceReasonController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
