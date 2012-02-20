package edu.sinclair.ssp.model.reference;

import java.util.UUID;


public class EducationLevel extends AbstractReference {

	public EducationLevel() {
		super();
	}

	public EducationLevel(UUID id, String name) {
		super(id, name);
	}

	public EducationLevel(UUID id, String name, String description) {
		super(id, name, description);
	}

}
