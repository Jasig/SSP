package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.ConfidentialityLevel;
import edu.sinclair.ssp.transferobject.TransferObject;

public class ConfidentialityLevelTO extends AbstractReferenceTO implements
		TransferObject<ConfidentialityLevel> {

	public ConfidentialityLevelTO() {
		super();
	}

	public ConfidentialityLevelTO(UUID id) {
		super(id);
	}

	public ConfidentialityLevelTO(UUID id, String name) {
		super(id, name);
	}

	public ConfidentialityLevelTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ConfidentialityLevelTO(ConfidentialityLevel model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(ConfidentialityLevel model) {
		super.fromModel(model);
	}

	@Override
	public ConfidentialityLevel pushAttributesToModel(ConfidentialityLevel model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ConfidentialityLevel asModel() {
		return pushAttributesToModel(new ConfidentialityLevel());
	}

}
