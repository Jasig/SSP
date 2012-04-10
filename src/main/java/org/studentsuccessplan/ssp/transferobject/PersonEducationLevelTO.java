package edu.sinclair.ssp.transferobject;

import edu.sinclair.ssp.model.PersonEducationLevel;

public class PersonEducationLevelTO implements
		TransferObject<PersonEducationLevel> {

	public PersonEducationLevelTO() {
		super();
	}

	public PersonEducationLevelTO(PersonEducationLevel model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(PersonEducationLevel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public PersonEducationLevel pushAttributesToModel(PersonEducationLevel model) {
		// TODO Auto-generated method stub
		return model;
	}

	@Override
	public PersonEducationLevel asModel() {
		return pushAttributesToModel(new PersonEducationLevel());
	}

}
