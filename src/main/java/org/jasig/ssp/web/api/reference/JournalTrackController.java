package org.jasig.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.JournalTrackTOFactory;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.transferobject.reference.JournalTrackTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/journalTrack")
public class JournalTrackController
		extends
		AbstractAuditableReferenceController<JournalTrack, JournalTrackTO> {

	@Autowired
	protected transient JournalTrackService service;

	@Override
	protected AuditableCrudService<JournalTrack> getService() {
		return service;
	}

	@Autowired
	protected transient JournalTrackTOFactory factory;

	@Override
	protected TOFactory<JournalTrackTO, JournalTrack> getFactory() {
		return factory;
	}

	protected JournalTrackController() {
		super(JournalTrack.class, JournalTrackTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalTrackController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
