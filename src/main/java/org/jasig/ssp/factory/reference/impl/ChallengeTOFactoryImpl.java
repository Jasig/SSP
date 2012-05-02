package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChallengeDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.ChallengeTOFactory;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

@Service
@Transactional(readOnly = true)
public class ChallengeTOFactoryImpl extends
		AbstractReferenceTOFactory<ChallengeTO, Challenge>
		implements ChallengeTOFactory {

	public ChallengeTOFactoryImpl() {
		super(ChallengeTO.class, Challenge.class);
	}

	@Autowired
	private transient ChallengeDao dao;

	@Override
	protected ChallengeDao getDao() {
		return dao;
	}

	@Override
	public Challenge from(final ChallengeTO tObject)
			throws ObjectNotFoundException {
		final Challenge model = super.from(tObject);

		model.setSelfHelpGuideDescription(tObject.getSelfHelpGuideDescription());
		model.setShowInSelfHelpSearch(tObject.isShowInSelfHelpSearch());
		model.setShowInStudentIntake(tObject.isShowInStudentIntake());
		model.setTags(tObject.getTags());

		return model;
	}
}
