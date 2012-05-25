package org.jasig.ssp.transferobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonFundingSource;

import com.google.common.collect.Lists;

public class PersonFundingSourceTO
		extends AbstractAuditableTO<PersonFundingSource>
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
		from(model);
	}

	@Override
	public final void from(final PersonFundingSource model) {
		super.from(model);

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

	public static List<PersonFundingSourceTO> toTOList(
			final Collection<PersonFundingSource> models) {
		final List<PersonFundingSourceTO> tos = Lists.newArrayList();
		for (final PersonFundingSource model : models) {
			tos.add(new PersonFundingSourceTO(model)); // NOPMD
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