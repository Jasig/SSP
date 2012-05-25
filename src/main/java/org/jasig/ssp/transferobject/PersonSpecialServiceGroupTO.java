package org.jasig.ssp.transferobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonSpecialServiceGroup;

import com.google.common.collect.Lists;

public class PersonSpecialServiceGroupTO
		extends AbstractAuditableTO<PersonSpecialServiceGroup>
		implements TransferObject<PersonSpecialServiceGroup> {

	@NotNull
	private UUID specialServiceGroupId;

	@NotNull
	private UUID personId;

	public PersonSpecialServiceGroupTO() {
		super();
	}

	public PersonSpecialServiceGroupTO(final PersonSpecialServiceGroup model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonSpecialServiceGroup model) {
		super.from(model);

		if (model.getSpecialServiceGroup() != null) {
			setSpecialServiceGroupId(model.getSpecialServiceGroup().getId());
		}

		if (model.getPerson() != null) {
			setPersonId(model.getPerson().getId());
		}
	}

	public static List<PersonSpecialServiceGroupTO> toTOList(
			final Collection<PersonSpecialServiceGroup> models) {
		final List<PersonSpecialServiceGroupTO> tos = Lists.newArrayList();
		for (final PersonSpecialServiceGroup model : models) {
			tos.add(new PersonSpecialServiceGroupTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getSpecialServiceGroupId() {
		return specialServiceGroupId;
	}

	public final void setSpecialServiceGroupId(final UUID specialServiceGroupId) {
		this.specialServiceGroupId = specialServiceGroupId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

}
