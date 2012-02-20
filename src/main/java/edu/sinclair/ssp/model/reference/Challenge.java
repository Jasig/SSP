package edu.sinclair.ssp.model.reference;

import java.util.UUID;



public class Challenge extends AbstractReference{

	public Challenge() {
		super();
	}
	
	public Challenge(UUID id, String name) {
		super(id, name);
	}

	public Challenge(UUID id, String name, String description) {
		super(id, name, description);
	}

}

