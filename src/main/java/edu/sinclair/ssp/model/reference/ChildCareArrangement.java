package edu.sinclair.ssp.model.reference;

import java.util.UUID;



public class ChildCareArrangement extends AbstractReference{

	public ChildCareArrangement(){
		super();
	}
	
	public ChildCareArrangement(UUID id, String name) {
		super(id, name);
	}
	
	public ChildCareArrangement(UUID id, String name, String description) {
		super(id, name, description);
	}
}
