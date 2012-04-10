package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.ChallengeReferral;
import edu.sinclair.ssp.transferobject.TransferObject;

public class ChallengeReferralTO extends AbstractReferenceTO<ChallengeReferral>
		implements TransferObject<ChallengeReferral> {

	private String publicDescription;

	public ChallengeReferralTO() {
		super();
	}

	public ChallengeReferralTO(UUID id) {
		super(id);
	}

	public ChallengeReferralTO(UUID id, String name) {
		super(id, name);
	}

	public ChallengeReferralTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ChallengeReferralTO(ChallengeReferral model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(ChallengeReferral model) {
		super.fromModel(model);
		setPublicDescription(model.getPublicDescription());
	}

	@Override
	public ChallengeReferral pushAttributesToModel(ChallengeReferral model) {
		super.addToModel(model);

		model.setPublicDescription(getPublicDescription());

		return model;
	}

	@Override
	public ChallengeReferral asModel() {
		return pushAttributesToModel(new ChallengeReferral());
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	public void setPublicDescription(String publicDescription) {
		this.publicDescription = publicDescription;
	}

}
