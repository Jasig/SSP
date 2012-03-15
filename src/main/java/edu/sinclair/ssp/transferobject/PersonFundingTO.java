package edu.sinclair.ssp.transferobject;

import edu.sinclair.ssp.model.PersonFunding;

public class PersonFundingTO implements TransferObject<PersonFunding>{

	public PersonFundingTO(){
		super();
	}

	public PersonFundingTO(PersonFunding model){
		super();
		pullAttributesFromModel(model);
	}
	
	@Override
	public void pullAttributesFromModel(PersonFunding model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonFunding pushAttributesToModel(PersonFunding model) {
		// TODO Auto-generated method stub
		return model;
	}
	
	@Override
	public PersonFunding asModel(){
		return pushAttributesToModel(new PersonFunding());
	}

}
