package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChallengeReferralDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.ChallengeReferralTOFactory;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeReferralTO;

@Service
@Transactional(readOnly = true)
public class ChallengeReferralTOFactoryImpl extends
		AbstractReferenceTOFactory<ChallengeReferralTO, ChallengeReferral>
		implements ChallengeReferralTOFactory {

	public ChallengeReferralTOFactoryImpl() {
		super(ChallengeReferralTO.class, ChallengeReferral.class);
	}

	@Autowired
	private ChallengeReferralDao dao;

	@Override
	protected ChallengeReferralDao getDao() {
		return dao;
	}

	@Override
	public ChallengeReferral from(ChallengeReferralTO tObject)
			throws ObjectNotFoundException {
		ChallengeReferral model = super.from(tObject);

		model.setPublicDescription(tObject.getPublicDescription());

		return model;
	}
}
