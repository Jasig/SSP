package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.transferobject.TransferObject;

public class FundingSourceTO extends AbstractReferenceTO<FundingSource>
		implements TransferObject<FundingSource> {

	public FundingSourceTO() {
		super();
	}

	public FundingSourceTO(UUID id) {
		super(id);
	}

	public FundingSourceTO(UUID id, String name) {
		super(id, name);
	}

	public FundingSourceTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public FundingSourceTO(FundingSource model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(FundingSource model) {
		super.fromModel(model);
	}

	@Override
	public FundingSource pushAttributesToModel(FundingSource model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public FundingSource asModel() {
		return pushAttributesToModel(new FundingSource());
	}

}
