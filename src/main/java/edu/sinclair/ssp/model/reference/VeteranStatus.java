package edu.sinclair.ssp.model.reference;

import java.util.UUID;



public class VeteranStatus extends AbstractReference{

	public VeteranStatus() {
		super();
	}
	
	public VeteranStatus(UUID id, String name){
		super(id, name);
	}
	
	public VeteranStatus(UUID id, String name, String description) {
		super(id, name, description);
	}
	
}
