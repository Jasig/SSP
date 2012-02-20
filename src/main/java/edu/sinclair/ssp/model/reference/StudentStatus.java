package edu.sinclair.ssp.model.reference;

import java.util.UUID;



public class StudentStatus extends AbstractReference {

	public StudentStatus() {
		super();
	}

	public StudentStatus(UUID id, String name) {
		super(id, name);
	}

	public StudentStatus(UUID id, String name, String description) {
		super(id, name, description);
	}

}
