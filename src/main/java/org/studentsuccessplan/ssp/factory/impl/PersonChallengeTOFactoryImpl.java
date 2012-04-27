package org.studentsuccessplan.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonChallengeDao;
import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.PersonChallengeTOFactory;
import org.studentsuccessplan.ssp.model.PersonChallenge;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.transferobject.PersonChallengeTO;

@Service
@Transactional(readOnly = true)
public class PersonChallengeTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonChallengeTO, PersonChallenge>
		implements PersonChallengeTOFactory {

	public PersonChallengeTOFactoryImpl() {
		super(PersonChallengeTO.class, PersonChallenge.class);
	}

	@Autowired
	private transient PersonChallengeDao dao;

	@Autowired
	private transient ChallengeService challengeService;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonChallengeDao getDao() {
		return dao;
	}

	@Override
	public PersonChallenge from(final PersonChallengeTO tObject)
			throws ObjectNotFoundException {
		final PersonChallenge model = super.from(tObject);

		model.setDescription(tObject.getDescription());

		if (tObject.getChallengeId() != null) {
			model.setChallenge(challengeService.get(tObject.getChallengeId()));
		}

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}
}
