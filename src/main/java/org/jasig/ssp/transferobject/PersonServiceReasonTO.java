package org.jasig.ssp.transferobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonServiceReason;

import com.google.common.collect.Lists;

public class PersonServiceReasonTO
		extends AbstractAuditableTO<PersonServiceReason>
		implements TransferObject<PersonServiceReason> {

	@NotNull
	private UUID serviceReasonId;

	@NotNull
	private UUID personId;

	public PersonServiceReasonTO() {
		super();
	}

	public PersonServiceReasonTO(final PersonServiceReason model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonServiceReason model) {
		super.from(model);

		if (model.getServiceReason() != null) {
			setServiceReasonId(model.getServiceReason().getId());
		}

		if (model.getPerson() != null) {
			setPersonId(model.getPerson().getId());
		}
	}

	public static List<PersonServiceReasonTO> toTOList(
			final Collection<PersonServiceReason> models) {
		final List<PersonServiceReasonTO> tos = Lists.newArrayList();
		for (final PersonServiceReason model : models) {
			tos.add(new PersonServiceReasonTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getServiceReasonId() {
		return serviceReasonId;
	}

	public final void setServiceReasonId(final UUID serviceReasonId) {
		this.serviceReasonId = serviceReasonId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

}
