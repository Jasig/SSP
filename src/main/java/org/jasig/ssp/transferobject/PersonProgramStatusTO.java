package org.jasig.ssp.transferobject;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonProgramStatus;

/**
 * PersonProgramStatus transfer object
 * 
 * @author jon.adams
 * 
 */
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

	/**
	 * Empty constructor
	 */
	public PersonProgramStatusTO() {
		super();
	}

	/**
	 * Construct a transfer object from the specified model
	 * 
	 * @param model
	 *            Copy this model to an equivalent transfer object
	 */
	public PersonProgramStatusTO(final PersonProgramStatus model) {
		super();
		from(model);
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
}