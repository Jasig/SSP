package org.jasig.ssp.web.api;

import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Person JournalEntry controller
 */
@Controller
@RequestMapping("/1/person/{personId}/journalEntry")
public class PersonJournalEntryController extends
		AbstractPersonAssocController<JournalEntry, JournalEntryTO> {

	/**
	 * Construct a controller instance with the specific class types used by the
	 * super class methods.
	 */
	protected PersonJournalEntryController() {
		super(JournalEntry.class, JournalEntryTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonJournalEntryController.class);

	@Autowired
	private transient JournalEntryService service;

	@Autowired
	private transient JournalEntryTOFactory factory;

	@Override
	protected JournalEntryTOFactory getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected JournalEntryService getService() {
		return service;
	}

	@Override
	public String permissionBaseName() {
		return "JOURNAL_ENTRY";
	}
}