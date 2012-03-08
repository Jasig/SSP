package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class MaritalStatusTO extends AbstractReferenceTO {

	public MaritalStatusTO () {
		super();
	}
	
	public MaritalStatusTO (UUID id) {
		super(id);
	}
	
	public MaritalStatusTO (UUID id, String name) {
		super(id, name);
	}

	public MaritalStatusTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
