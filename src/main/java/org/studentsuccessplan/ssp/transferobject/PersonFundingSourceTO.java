package org.studentsuccessplan.ssp.transferobject;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonFundingSource;
import org.studentsuccessplan.ssp.model.reference.FundingSource;

import com.google.common.collect.Lists;

public class PersonFundingSourceTO
		extends AuditableTO<PersonFundingSource>
		implements TransferObject<PersonFundingSource> {

	private UUID personId, fundingSourceId;
	private String description;

	public PersonFundingSourceTO() {
		super();
	}

	public PersonFundingSourceTO(PersonFundingSource model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(PersonFundingSource model) {
		super.fromModel(model);

		setDescription(model.getDescription());

		if ((model.getFundingSource() != null)
				&& (model.getFundingSource().getId() != null)) {
			setFundingSourceId(model.getFundingSource().getId());
		}

		if ((model.getPerson() != null)
				&& (model.getPerson().getId() != null)) {
			setPersonId(model.getPerson().getId());
		}
	}

	@Override
	public PersonFundingSource addToModel(PersonFundingSource model) {
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
		return addToModel(new PersonFundingSource());
	}

	public static List<PersonFundingSourceTO> listToTOList(
			List<PersonFundingSource> models) {
		List<PersonFundingSourceTO> tos = Lists.newArrayList();
		for (PersonFundingSource model : models) {
			tos.add(new PersonFundingSourceTO(model));
		}
		return tos;
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
