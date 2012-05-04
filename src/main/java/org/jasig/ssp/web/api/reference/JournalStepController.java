package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.JournalStepTOFactory;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.JournalStepService;
import org.jasig.ssp.transferobject.reference.JournalStepTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/journalStep")
public class JournalStepController
		extends
		AbstractAuditableReferenceController<JournalStep, JournalStepTO> {

	@Autowired
	protected transient JournalStepService service;

	@Override
	protected AuditableCrudService<JournalStep> getService() {
		return service;
	}

	@Autowired
	protected transient JournalStepTOFactory factory;

	@Override
	protected TOFactory<JournalStepTO, JournalStep> getFactory() {
		return factory;
	}

	protected JournalStepController() {
		super(JournalStep.class, JournalStepTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalStepController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
