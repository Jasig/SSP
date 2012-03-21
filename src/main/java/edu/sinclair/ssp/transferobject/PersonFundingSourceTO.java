package edu.sinclair.ssp.transferobject;

import edu.sinclair.ssp.model.PersonFundingSource;

public class PersonFundingSourceTO implements TransferObject<PersonFundingSource>{

	public PersonFundingSourceTO(){
		super();
	}

	public PersonFundingSourceTO(PersonFundingSource model){
		super();
		pullAttributesFromModel(model);
	}
	
	@Override
	public void pullAttributesFromModel(PersonFundingSource model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonFundingSource pushAttributesToModel(PersonFundingSource model) {
		// TODO Auto-generated method stub
		return model;
	}
	
	@Override
	public PersonFundingSource asModel(){
		return pushAttributesToModel(new PersonFundingSource());
	}

}
