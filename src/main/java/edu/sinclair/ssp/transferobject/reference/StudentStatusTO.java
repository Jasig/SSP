package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class StudentStatusTO extends AbstractReferenceTO {

	public StudentStatusTO () {
		super();
	}
	
	public StudentStatusTO (UUID id) {
		super(id);
	}
	
	public StudentStatusTO (UUID id, String name) {
		super(id, name);
	}

	public StudentStatusTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
