package edu.sinclair.ssp.model.reference;

import java.util.UUID;



public class Ethnicity extends AbstractReference{

	public Ethnicity() {
		super();
	}
	
	public Ethnicity(UUID id, String name) {
		super(id, name);
	}
	
	public Ethnicity(UUID id, String name, String description) {
		super(id, name, description);
	}
	
}
