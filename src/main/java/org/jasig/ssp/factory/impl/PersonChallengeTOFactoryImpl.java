package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonChallengeDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonChallengeTOFactory;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.PersonChallengeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		model.setPerson(tObject.getPersonId() == null ? null :
				personService.get(tObject.getPersonId()));
		model.setChallenge(tObject.getChallengeId() == null ? null :
				challengeService.get(tObject.getChallengeId()));

		return model;
	}
}