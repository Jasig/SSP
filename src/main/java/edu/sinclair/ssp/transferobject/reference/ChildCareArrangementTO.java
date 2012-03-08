package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class ChildCareArrangementTO extends AbstractReferenceTO {

	public ChildCareArrangementTO () {
		super();
	}
	
	public ChildCareArrangementTO (UUID id) {
		super(id);
	}
	
	public ChildCareArrangementTO (UUID id, String name) {
		super(id, name);
	}

	public ChildCareArrangementTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
