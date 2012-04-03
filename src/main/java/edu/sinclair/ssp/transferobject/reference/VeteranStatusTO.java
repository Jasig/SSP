package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.transferobject.TransferObject;

public class VeteranStatusTO extends AbstractReferenceTO<VeteranStatus>
		implements TransferObject<VeteranStatus> {

	public VeteranStatusTO() {
		super();
	}

	public VeteranStatusTO(UUID id) {
		super(id);
	}

	public VeteranStatusTO(UUID id, String name) {
		super(id, name);
	}

	public VeteranStatusTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public VeteranStatusTO(VeteranStatus model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(VeteranStatus model) {
		super.fromModel(model);
	}

	@Override
	public VeteranStatus pushAttributesToModel(VeteranStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public VeteranStatus asModel() {
		return pushAttributesToModel(new VeteranStatus());
	}

}
