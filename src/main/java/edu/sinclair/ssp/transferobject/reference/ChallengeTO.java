package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.transferobject.TransferObject;

public class ChallengeTO extends AbstractReferenceTO<Challenge> implements
		TransferObject<Challenge> {

	public ChallengeTO() {
		super();
	}

	public ChallengeTO(UUID id) {
		super(id);
	}

	public ChallengeTO(UUID id, String name) {
		super(id, name);
	}

	public ChallengeTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ChallengeTO(Challenge model) {
		super(model);
	}

	@Override
	public Challenge asModel() {
		return pushAttributesToModel(new Challenge());
	}
}
