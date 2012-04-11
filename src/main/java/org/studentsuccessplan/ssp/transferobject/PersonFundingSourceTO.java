package org.studentsuccessplan.ssp.transferobject;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonFundingSource;
import org.studentsuccessplan.ssp.model.reference.FundingSource;

public class PersonFundingSourceTO extends AuditableTO<PersonFundingSource> 
		implements TransferObject<PersonFundingSource> {

	private UUID personId, fundingSourceId;
	private String description;
	
	public PersonFundingSourceTO() {
		super();
	}

	public PersonFundingSourceTO(PersonFundingSource model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(PersonFundingSource model) {
		super.fromModel(model);

		setDescription(model.getDescription());
		
		if (model.getFundingSource() != null 
				&& model.getFundingSource().getId() != null) {
			setFundingSourceId(model.getFundingSource().getId());
		}

		if (model.getPerson() != null 
				&& model.getPerson().getId() != null) {
			setPersonId(model.getPerson().getId());
		}
	}

	@Override
	public PersonFundingSource pushAttributesToModel(PersonFundingSource model) {
		super.addToModel(model);

		model.setDescription(getDescription());
		
		if (getFundingSourceId() != null) {
			model.setFundingSource(new FundingSource(getFundingSourceId()));
		}
		
		if (getPersonId() != null) {
			model.setPerson(new Person(getPersonId()));
		}
		
		return model;
	}

	@Override
	public PersonFundingSource asModel() {
		return pushAttributesToModel(new PersonFundingSource());
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public UUID getFundingSourceId() {
		return fundingSourceId;
	}

	public void setFundingSourceId(UUID fundingSourceId) {
		this.fundingSourceId = fundingSourceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
