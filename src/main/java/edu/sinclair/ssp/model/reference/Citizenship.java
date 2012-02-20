package edu.sinclair.ssp.model.reference;

import java.util.UUID;



public class Citizenship extends AbstractReference{

	public Citizenship(){
		super();
	}
	
	public Citizenship(UUID id, String name) {
		super(id, name);
	}
	
	public Citizenship(UUID id, String name, String description) {
		super(id, name, description);
	}
}
