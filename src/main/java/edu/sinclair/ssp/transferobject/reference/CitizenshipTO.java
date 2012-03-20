package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.transferobject.TransferObject;

public class CitizenshipTO extends AbstractReferenceTO implements
		TransferObject<Citizenship> {

	public CitizenshipTO() {
		super();
	}

	public CitizenshipTO(UUID id) {
		super(id);
	}

	public CitizenshipTO(UUID id, String name) {
		super(id, name);
	}

	public CitizenshipTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public CitizenshipTO(Citizenship model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(Citizenship model) {
		super.fromModel(model);
	}

	@Override
	public Citizenship pushAttributesToModel(Citizenship model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Citizenship asModel() {
		return pushAttributesToModel(new Citizenship());
	}

}
