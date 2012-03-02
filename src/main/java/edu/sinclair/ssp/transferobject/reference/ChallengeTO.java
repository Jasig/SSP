package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

public class ChallengeTO extends AbstractReferenceTO {

	public ChallengeTO () {
		super();
	}
	
	public ChallengeTO (UUID id) {
		super(id);
	}
	
	public ChallengeTO (UUID id, String name) {
		super(id, name);
	}

	public ChallengeTO (UUID id, String name, String description) {
		super(id, name, description);
	}
}
