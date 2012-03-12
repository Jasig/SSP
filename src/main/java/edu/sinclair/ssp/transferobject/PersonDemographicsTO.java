package edu.sinclair.ssp.transferobject;

import edu.sinclair.ssp.model.PersonDemographics;

public class PersonDemographicsTO implements TransferObject<PersonDemographics>{

	public PersonDemographicsTO(){
		super();
	}

	public PersonDemographicsTO(PersonDemographics model){
		super();
		pullAttributesFromModel(model);
	}
	
	@Override
	public void pullAttributesFromModel(PersonDemographics model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonDemographics pushAttributesToModel(PersonDemographics model) {
		// TODO Auto-generated method stub
		return model;
	}
	
	@Override
	public PersonDemographics asModel(){
		return pushAttributesToModel(new PersonDemographics());
	}

}
