package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class EthnicityTO extends AbstractReferenceTO {

	public EthnicityTO () {
		super();
	}
	
	public EthnicityTO (UUID id) {
		super(id);
	}
	
	public EthnicityTO (UUID id, String name) {
		super(id, name);
	}

	public EthnicityTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
