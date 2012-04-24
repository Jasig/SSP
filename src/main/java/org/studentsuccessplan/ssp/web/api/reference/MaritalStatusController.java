package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.MaritalStatusService;
import org.studentsuccessplan.ssp.transferobject.reference.MaritalStatusTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/maritalStatus")
public class MaritalStatusController extends
		AbstractAuditableReferenceController<MaritalStatus, MaritalStatusTO> {

	@Autowired
	protected transient MaritalStatusService service;

	@Override
	protected AuditableCrudService<MaritalStatus> getService() {
		return service;
	}

	protected MaritalStatusController() {
		super(MaritalStatus.class, MaritalStatusTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MaritalStatusController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
