package org.jasig.ssp.transferobject;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonProgramStatus;

import com.google.common.collect.Lists;

public class PersonProgramStatusTO
		extends AbstractAuditableTO<PersonProgramStatus>
		implements TransferObject<PersonProgramStatus> {

	@NotNull
	private UUID personId;

	@NotNull
	private UUID programStatusId;

	private UUID programStatusChangeReasonId;

	@NotNull
	private Date effectiveDate;

	private Date expirationDate;

	public PersonProgramStatusTO() {
		super();
	}

	public PersonProgramStatusTO(final PersonProgramStatus model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonProgramStatus model) {
		super.from(model);

		if (model.getPerson() != null) {
			setPersonId(model.getPerson().getId());
		}

		if (model.getProgramStatus() != null) {
			setProgramStatusId(model.getProgramStatus().getId());
		}

		if (model.getProgramStatusChangeReason() != null) {
			setProgramStatusChangeReasonId(model.getProgramStatusChangeReason()
					.getId());
		}

		setEffectiveDate(model.getEffectiveDate());
		setExpirationDate(model.getExpirationDate());
	}

	public static List<PersonProgramStatusTO> toTOList(
			final Collection<PersonProgramStatus> models) {
		final List<PersonProgramStatusTO> tos = Lists.newArrayList();
		for (final PersonProgramStatus model : models) {
			tos.add(new PersonProgramStatusTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getProgramStatusId() {
		return programStatusId;
	}

	public final void setProgramStatusId(final UUID programStatusId) {
		this.programStatusId = programStatusId;
	}

	public UUID getProgramStatusChangeReasonId() {
		return programStatusChangeReasonId;
	}

	public final void setProgramStatusChangeReasonId(
			final UUID programStatusChangeReasonId) {
		this.programStatusChangeReasonId = programStatusChangeReasonId;
	}

	public Date getEffectiveDate() {
		return effectiveDate == null ? null : new Date(
				effectiveDate.getTime());
	}

	public final void setEffectiveDate(@NotNull final Date effectiveDate) {
		this.effectiveDate = effectiveDate == null ? null : new Date(
				effectiveDate.getTime());
	}

	public Date getExpirationDate() {
		return expirationDate == null ? null : new Date(
				expirationDate.getTime());
	}

	public final void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate == null ? null : new Date(
				expirationDate.getTime());
	}
}