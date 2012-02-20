package edu.sinclair.ssp.model.reference;

import java.util.UUID;

public class EducationGoal extends AbstractReference {

	public EducationGoal() {
		super();
	}

	public EducationGoal(UUID id, String name) {
		super(id, name);
	}

	public EducationGoal(UUID id, String name, String description) {
		super(id, name, description);
	}

}
