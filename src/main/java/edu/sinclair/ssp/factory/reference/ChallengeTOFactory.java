package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;

public interface ChallengeTOFactory extends TransferObjectFactory<ChallengeTO, Challenge> {

	@Override
	public ChallengeTO toTO(Challenge from);

	@Override
	public Challenge toModel(ChallengeTO from);

	@Override
	public List<ChallengeTO> toTOList(List<Challenge> from);

	@Override
	public List<Challenge> toModelList(List<ChallengeTO> from);

}