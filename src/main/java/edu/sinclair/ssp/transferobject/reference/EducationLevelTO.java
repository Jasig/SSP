package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.transferobject.TransferObject;

public class EducationLevelTO extends AbstractReferenceTO<EducationLevel>
		implements TransferObject<EducationLevel> {

	public EducationLevelTO() {
		super();
	}

	public EducationLevelTO(UUID id) {
		super(id);
	}

	public EducationLevelTO(UUID id, String name) {
		super(id, name);
	}

	public EducationLevelTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public EducationLevelTO(EducationLevel model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(EducationLevel model) {
		super.fromModel(model);
	}

	@Override
	public EducationLevel pushAttributesToModel(EducationLevel model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public EducationLevel asModel() {
		return pushAttributesToModel(new EducationLevel());
	}

}
