package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ProgramStatusChangeReasonTOFactory;
import org.jasig.ssp.model.reference.ProgramStatusChangeReason;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ProgramStatusChangeReasonService;
import org.jasig.ssp.transferobject.reference.ProgramStatusChangeReasonTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/programStatusChangeReason")
public class ProgramStatusChangeReasonController
		extends
		AbstractAuditableReferenceController<ProgramStatusChangeReason, ProgramStatusChangeReasonTO> {

	@Autowired
	protected transient ProgramStatusChangeReasonService service;

	@Override
	protected AuditableCrudService<ProgramStatusChangeReason> getService() {
		return service;
	}

	@Autowired
	protected transient ProgramStatusChangeReasonTOFactory factory;

	@Override
	protected TOFactory<ProgramStatusChangeReasonTO, ProgramStatusChangeReason> getFactory() {
		return factory;
	}

	protected ProgramStatusChangeReasonController() {
		super(ProgramStatusChangeReason.class, ProgramStatusChangeReasonTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProgramStatusChangeReasonController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
