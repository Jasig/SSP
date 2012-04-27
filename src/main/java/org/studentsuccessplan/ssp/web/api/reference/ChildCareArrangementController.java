package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.factory.TOFactory;
import org.studentsuccessplan.ssp.factory.reference.ChildCareArrangementTOFactory;
import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.ChildCareArrangementService;
import org.studentsuccessplan.ssp.transferobject.reference.ChildCareArrangementTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/childCareArrangement")
public class ChildCareArrangementController
		extends
		AbstractAuditableReferenceController<ChildCareArrangement, ChildCareArrangementTO> {

	@Autowired
	protected transient ChildCareArrangementService service;

	@Override
	protected AuditableCrudService<ChildCareArrangement> getService() {
		return service;
	}

	@Autowired
	protected transient ChildCareArrangementTOFactory factory;

	@Override
	protected TOFactory<ChildCareArrangementTO, ChildCareArrangement> getFactory() {
		return factory;
	}

	protected ChildCareArrangementController() {
		super(ChildCareArrangement.class, ChildCareArrangementTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChildCareArrangementController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
