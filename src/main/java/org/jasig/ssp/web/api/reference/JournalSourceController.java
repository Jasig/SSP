package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.JournalSourceTOFactory;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.transferobject.reference.JournalSourceTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/journalSource")
public class JournalSourceController
		extends
		AbstractAuditableReferenceController<JournalSource, JournalSourceTO> {

	@Autowired
	protected transient JournalSourceService service;

	@Override
	protected AuditableCrudService<JournalSource> getService() {
		return service;
	}

	@Autowired
	protected transient JournalSourceTOFactory factory;

	@Override
	protected TOFactory<JournalSourceTO, JournalSource> getFactory() {
		return factory;
	}

	protected JournalSourceController() {
		super(JournalSource.class, JournalSourceTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalSourceController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
