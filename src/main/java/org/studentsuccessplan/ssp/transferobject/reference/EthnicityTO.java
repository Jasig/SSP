package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.transferobject.TransferObject;

public class EthnicityTO extends AbstractReferenceTO<Ethnicity> implements
		TransferObject<Ethnicity> {

	public EthnicityTO() {
		super();
	}

	public EthnicityTO(UUID id) {
		super(id);
	}

	public EthnicityTO(UUID id, String name) {
		super(id, name);
	}

	public EthnicityTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public EthnicityTO(Ethnicity model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(Ethnicity model) {
		super.fromModel(model);
	}

	@Override
	public Ethnicity pushAttributesToModel(Ethnicity model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Ethnicity asModel() {
		return pushAttributesToModel(new Ethnicity());
	}

}
