package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class CitizenshipTO extends AbstractReferenceTO {

	public CitizenshipTO () {
		super();
	}
	
	public CitizenshipTO (UUID id) {
		super(id);
	}
	
	public CitizenshipTO (UUID id, String name) {
		super(id, name);
	}

	public CitizenshipTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
