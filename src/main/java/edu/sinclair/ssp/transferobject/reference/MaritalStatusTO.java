package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.transferobject.TransferObject;

public class MaritalStatusTO extends AbstractReferenceTO implements
		TransferObject<MaritalStatus> {

	public MaritalStatusTO() {
		super();
	}

	public MaritalStatusTO(UUID id) {
		super(id);
	}

	public MaritalStatusTO(UUID id, String name) {
		super(id, name);
	}

	public MaritalStatusTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public MaritalStatusTO(MaritalStatus model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(MaritalStatus model) {
		super.fromModel(model);
	}

	@Override
	public MaritalStatus pushAttributesToModel(MaritalStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public MaritalStatus asModel() {
		return pushAttributesToModel(new MaritalStatus());
	}

}
