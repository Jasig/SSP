package org.jasig.ssp.web.api;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/person/{personId}/journalEntry")
public class PersonJournalEntryController extends
		AbstractPersonAssocController<JournalEntry, JournalEntryTO> {

	protected PersonJournalEntryController() {
		super(JournalEntry.class, JournalEntryTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonJournalEntryController.class);

	@Autowired
	private transient JournalEntryService service;

	@Override
	protected TOFactory<JournalEntryTO, JournalEntry> getFactory() {
		return null;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected JournalEntryService getService() {
		return service;
	}
}
