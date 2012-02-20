package edu.sinclair.ssp.model.reference;

import java.util.UUID;



public class MaritalStatus extends AbstractReference {

	public MaritalStatus() {
		super();
	}

	public MaritalStatus(UUID id, String name) {
		super(id, name);
	}

	public MaritalStatus(UUID id, String name, String description) {
		super(id, name, description);
	}

}
