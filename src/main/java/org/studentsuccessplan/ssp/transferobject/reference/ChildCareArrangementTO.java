package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

public class ChildCareArrangementTO extends
		AbstractReferenceTO<ChildCareArrangement> implements
		TransferObject<ChildCareArrangement> {

	public ChildCareArrangementTO() {
		super();
	}

	public ChildCareArrangementTO(UUID id) {
		super(id);
	}

	public ChildCareArrangementTO(UUID id, String name) {
		super(id, name);
	}

	public ChildCareArrangementTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ChildCareArrangementTO(ChildCareArrangement model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(ChildCareArrangement model) {
		super.fromModel(model);
	}

	@Override
	public ChildCareArrangement pushAttributesToModel(ChildCareArrangement model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ChildCareArrangement asModel() {
		return pushAttributesToModel(new ChildCareArrangement());
	}

}
