package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ChallengeReferralDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;

@Service
@Transactional(readOnly = true)
public class ChallengeReferralTOFactoryImpl extends
		AbstractReferenceTOFactory<ChallengeReferralTO, ChallengeReferral>
		implements ChallengeReferralTOFactory {

	public ChallengeReferralTOFactoryImpl() {
		super(ChallengeReferralTO.class, ChallengeReferral.class);
	}

	@Autowired
	private transient ChallengeReferralDao dao;

	@Override
	protected ChallengeReferralDao getDao() {
		return dao;
	}

	@Override
	public ChallengeReferral from(final ChallengeReferralTO tObject)
			throws ObjectNotFoundException {
		final ChallengeReferral model = super.from(tObject);

		model.setPublicDescription(tObject.getPublicDescription());

		return model;
	}
}
