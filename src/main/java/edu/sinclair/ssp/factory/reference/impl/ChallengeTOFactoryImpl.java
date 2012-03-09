package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.ChallengeTOFactory;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;

@Service
public class ChallengeTOFactoryImpl 
			extends AbstractTransferObjectFactory<ChallengeTO, Challenge>
			implements ChallengeTOFactory{

	@Override
	public ChallengeTO toTO(Challenge from) {
		ChallengeTO to = new ChallengeTO();
		
		to.fromModel(from);
		
		return to;
	}

	@Override
	public Challenge toModel(ChallengeTO from) {
		Challenge model = new Challenge();
		
		from.addToModel(model);
		
		return model;
	}

}
