package org.jasig.ssp.factory.reference.impl;

import java.util.UUID;

import org.jasig.ssp.factory.AbstractTOFactory;
import org.jasig.ssp.factory.reference.ChallengeReferralSearchResultTOFactory;
import org.jasig.ssp.model.reference.ChallengeReferralSearchResult;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ChallengeReferralSearchResultTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ChallengeReferralSearchResultTOFactoryImpl
		extends
		AbstractTOFactory<ChallengeReferralSearchResult, ChallengeReferralSearchResultTO> implements ChallengeReferralSearchResultTOFactory {
	
	public ChallengeReferralSearchResultTOFactoryImpl() {
		super(ChallengeReferralSearchResultTO.class, ChallengeReferralSearchResult.class);
	}

	@Override
	public ChallengeReferralSearchResult from(
			ChallengeReferralSearchResultTO tObject)
			throws ObjectNotFoundException {
		ChallengeReferralSearchResult model = new ChallengeReferralSearchResult();
		model.setCategoryId(tObject.getCategoryId());
		model.setCategoryName(tObject.getCategoryName());
		model.setChallengeId(tObject.getChallengeId());
		model.setChallengeName(tObject.getChallengeName());
		model.setChallengeReferralId(tObject.getChallengeReferralId());
		model.setChallengeReferralName(tObject.getChallengeReferralName());
		model.setChallengeReferralDescription(tObject.getChallengReferralDescription());
		return model;
	}

	@Override
	public ChallengeReferralSearchResult from(UUID id)
			throws ObjectNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


}
