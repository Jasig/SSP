package edu.sinclair.ssp.transferobject;

import edu.sinclair.ssp.model.PersonEducationPlan;

public class PersonEducationPlanTO implements TransferObject<PersonEducationPlan>{

	public PersonEducationPlanTO(){
		super();
	}

	public PersonEducationPlanTO(PersonEducationPlan model){
		super();
		pullAttributesFromModel(model);
	}
	
	@Override
	public void pullAttributesFromModel(PersonEducationPlan model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonEducationPlan pushAttributesToModel(PersonEducationPlan model) {
		// TODO Auto-generated method stub
		return model;
	}
	
	@Override
	public PersonEducationPlan asModel(){
		return pushAttributesToModel(new PersonEducationPlan());
	}

}
