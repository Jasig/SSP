package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.JournalEntryDao;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.JournalEntryDetail;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.AbstractRestrictedPersonAssocAuditableService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JournalEntryServiceImpl
		extends AbstractRestrictedPersonAssocAuditableService<JournalEntry>
		implements JournalEntryService {

	@Autowired
	private transient JournalEntryDao dao;

	@Autowired
	private transient PersonProgramStatusService personProgramStatusService;

	@Override
	protected JournalEntryDao getDao() {
		return dao;
	}

	@Override
	public JournalEntry create(final JournalEntry obj)
			throws ObjectNotFoundException, ValidationException {
		final JournalEntry journalEntry = getDao().save(obj);
		checkForTransition(journalEntry);
		return journalEntry;
	}

	@Override
	public JournalEntry save(final JournalEntry obj)
			throws ObjectNotFoundException, ValidationException {
		final JournalEntry journalEntry = getDao().save(obj);
		checkForTransition(journalEntry);
		return journalEntry;
	}

	private void checkForTransition(final JournalEntry journalEntry)
			throws ObjectNotFoundException, ValidationException {
		// search for a JournalStep that indicates a transition
		for (final JournalEntryDetail detail : journalEntry
				.getJournalEntryDetails()) {
			if (detail.getJournalStepJournalStepDetail().getJournalStep()
					.isUsedForTransition()) {
				// is used for transition, so attempt to set program status
				personProgramStatusService.setTransitionForStudent(journalEntry
						.getPerson());

				// exit early because no need to loop through others
				return;
			}
		}
	}
	
	public Long getCountForCoach(Person currPerson){
		return dao.getJournalCountForCoach(currPerson);
	}

	@Override
	public Long getStudentCountForCoach(Person currPerson) {
		// TODO Auto-generated method stub
		return dao.getStudentJournalCountForCoach(currPerson);
	}
}