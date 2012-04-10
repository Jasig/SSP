package org.studentsuccessplan.ssp.transferobject;

import org.studentsuccessplan.ssp.model.PersonChallenge;

public class PersonChallengeTO implements TransferObject<PersonChallenge> {

	public PersonChallengeTO() {
		super();
	}

	public PersonChallengeTO(PersonChallenge model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(PersonChallenge model) {
		// TODO Auto-generated method stub

	}

	@Override
	public PersonChallenge pushAttributesToModel(PersonChallenge model) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public PersonChallenge asModel() {
		return pushAttributesToModel(new PersonChallenge());
	}

}
