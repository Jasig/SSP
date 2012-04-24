package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.CitizenshipService;
import org.studentsuccessplan.ssp.transferobject.reference.CitizenshipTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/citizenship")
public class CitizenshipController extends
		AbstractAuditableReferenceController<Citizenship, CitizenshipTO> {

	@Autowired
	protected transient CitizenshipService service;

	@Override
	protected AuditableCrudService<Citizenship> getService() {
		return service;
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
