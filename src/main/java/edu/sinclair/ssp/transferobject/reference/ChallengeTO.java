package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.transferobject.TransferObject;

public class ChallengeTO extends AbstractReferenceTO implements
TransferObject<Challenge> {

	private long referralCount;

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
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(Challenge model) {
		super.fromModel(model);
	}

	@Override
	public Challenge pushAttributesToModel(Challenge model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Challenge asModel() {
		return pushAttributesToModel(new Challenge());
	}


	public long getReferralCount() {
		return referralCount;
	}

	public void setReferralCount(long referralCount) {
		this.referralCount = referralCount;
	}
}
