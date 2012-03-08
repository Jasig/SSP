package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class VeteranStatusTO extends AbstractReferenceTO {

	public VeteranStatusTO () {
		super();
	}
	
	public VeteranStatusTO (UUID id) {
		super(id);
	}
	
	public VeteranStatusTO (UUID id, String name) {
		super(id, name);
	}

	public VeteranStatusTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
