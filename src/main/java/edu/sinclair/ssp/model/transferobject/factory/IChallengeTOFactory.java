package edu.sinclair.ssp.model.transferobject.factory;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.model.transferobject.reference.ChallengeTO;

@Transactional(readOnly = true)
public interface IChallengeTOFactory {

	//@Override
	public ChallengeTO toTO(Challenge from);

	//@Override
	public Challenge toModel(ChallengeTO from);

	//@Override
	public List<ChallengeTO> toTOList(List<Challenge> from);

	//@Override
	public List<Challenge> toModelList(List<ChallengeTO> from);

}