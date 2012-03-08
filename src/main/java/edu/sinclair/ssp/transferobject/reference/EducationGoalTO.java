package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class EducationGoalTO extends AbstractReferenceTO {

	public EducationGoalTO () {
		super();
	}
	
	public EducationGoalTO (UUID id) {
		super(id);
	}
	
	public EducationGoalTO (UUID id, String name) {
		super(id, name);
	}

	public EducationGoalTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
