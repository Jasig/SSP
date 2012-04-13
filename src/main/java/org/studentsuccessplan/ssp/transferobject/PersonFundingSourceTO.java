package org.studentsuccessplan.ssp.transferobject;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonFundingSource;
import org.studentsuccessplan.ssp.model.reference.FundingSource;

import com.google.common.collect.Lists;

public class PersonFundingSourceTO
		extends AuditableTO<PersonFundingSource>
		implements TransferObject<PersonFundingSource> {

	@NotNull
	private UUID fundingSourceId;

	@NotNull
	private UUID personId;

	private String description;

	public PersonFundingSourceTO() {
		super();
	}

	public PersonFundingSourceTO(final PersonFundingSource model) {
		super();
		fromModel(model);
	}

	@Override
	public final void fromModel(final PersonFundingSource model) {
		super.fromModel(model);

		description = model.getDescription();

		if ((model.getFundingSource() != null)
				&& (model.getFundingSource().getId() != null)) {
			fundingSourceId = model.getFundingSource().getId();
		}

		if ((model.getPerson() != null)
				&& (model.getPerson().getId() != null)) {
			personId = model.getPerson().getId();
		}
	}

	@Override
	public PersonFundingSource addToModel(final PersonFundingSource model) {
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
			final List<PersonFundingSource> models) {
		final List<PersonFundingSourceTO> tos = Lists.newArrayList();
		for (PersonFundingSource model : models) {
			tos.add(new PersonFundingSourceTO(model));
		}
		return tos;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getFundingSourceId() {
		return fundingSourceId;
	}

	public void setFundingSourceId(final UUID fundingSourceId) {
		this.fundingSourceId = fundingSourceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
