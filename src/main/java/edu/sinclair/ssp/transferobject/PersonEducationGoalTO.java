package edu.sinclair.ssp.transferobject;

import edu.sinclair.ssp.model.PersonEducationGoal;

public class PersonEducationGoalTO implements TransferObject<PersonEducationGoal>{

	public PersonEducationGoalTO(){
		super();
	}

	public PersonEducationGoalTO(PersonEducationGoal model){
		super();
		pullAttributesFromModel(model);
	}
	
	@Override
	public void pullAttributesFromModel(PersonEducationGoal model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonEducationGoal pushAttributesToModel(PersonEducationGoal model) {
		// TODO Auto-generated method stub
		return model;
	}
	
	@Override
	public PersonEducationGoal asModel(){
		return pushAttributesToModel(new PersonEducationGoal());
	}

}
