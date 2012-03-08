package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class EducationLevelTO extends AbstractReferenceTO {

	public EducationLevelTO () {
		super();
	}
	
	public EducationLevelTO (UUID id) {
		super(id);
	}
	
	public EducationLevelTO (UUID id, String name) {
		super(id, name);
	}

	public EducationLevelTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
