package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class FundingSourceTO extends AbstractReferenceTO {

	public FundingSourceTO () {
		super();
	}
	
	public FundingSourceTO (UUID id) {
		super(id);
	}
	
	public FundingSourceTO (UUID id, String name) {
		super(id, name);
	}

	public FundingSourceTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
