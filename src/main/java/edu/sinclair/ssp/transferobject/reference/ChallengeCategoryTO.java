package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.ChallengeCategory;
import edu.sinclair.ssp.transferobject.TransferObject;

public class ChallengeCategoryTO extends AbstractReferenceTO<ChallengeCategory>
		implements TransferObject<ChallengeCategory> {

	public ChallengeCategoryTO() {
		super();
	}

	public ChallengeCategoryTO(UUID id) {
		super(id);
	}

	public ChallengeCategoryTO(UUID id, String name) {
		super(id, name);
	}

	public ChallengeCategoryTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ChallengeCategoryTO(ChallengeCategory model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(ChallengeCategory model) {
		super.fromModel(model);
	}

	@Override
	public ChallengeCategory pushAttributesToModel(ChallengeCategory model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ChallengeCategory asModel() {
		return pushAttributesToModel(new ChallengeCategory());
	}

}
