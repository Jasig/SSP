package edu.sinclair.ssp.model.reference;

import java.util.UUID;



public class FundingSource extends AbstractReference{

	public FundingSource() {
		super();
	}
	
	public FundingSource(UUID id, String name) {
		super(id, name);
	}
	
	public FundingSource(UUID id, String name, String description) {
		super(id, name, description);
	}
}
