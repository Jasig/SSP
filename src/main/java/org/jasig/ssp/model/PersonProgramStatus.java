package org.jasig.ssp.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.ProgramStatusChangeReason;

/**
 * Assign a Person to a ProgramStatus
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonProgramStatus
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -8737080620762696441L;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "program_status_id", updatable = false, nullable = false)
	private ProgramStatus programStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "program_status_change_reason_id", updatable = false, nullable = true)
	private ProgramStatusChangeReason programStatusChangeReason;

	@NotNull
	private Date effectiveDate;

	private Date expirationDate;

	public PersonProgramStatus() {
		super();
	}

	public PersonProgramStatus(@NotNull final UUID id,
			@NotNull final ObjectStatus objectStatus,
			@NotNull final Person person,
			@NotNull final ProgramStatus programStatus,
			final ProgramStatusChangeReason programStatusChangeReason,
			@NotNull final Date effectiveDate, final Date expirationDate) {
		super();
		super.setId(id);
		super.setObjectStatus(objectStatus);
		this.person = person;
		this.programStatus = programStatus;
		this.programStatusChangeReason = programStatusChangeReason;
		this.effectiveDate = effectiveDate == null ? null : new Date(
				effectiveDate.getTime());
		this.expirationDate = expirationDate == null ? null : new Date(
				expirationDate.getTime());
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(@NotNull final Person person) {
		this.person = person;
	}

	public ProgramStatus getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(@NotNull final ProgramStatus programStatus) {
		this.programStatus = programStatus;
	}

	public ProgramStatusChangeReason getProgramStatusChangeReason() {
		return programStatusChangeReason;
	}

	public void setProgramStatusChangeReason(
			final ProgramStatusChangeReason programStatusChangeReason) {
		this.programStatusChangeReason = programStatusChangeReason;
	}

	public Date getEffectiveDate() {
		return effectiveDate == null ? null : new Date(effectiveDate.getTime());
	}

	public void setEffectiveDate(@NotNull final Date effectiveDate) {
		this.effectiveDate = effectiveDate == null ? null : new Date(
				effectiveDate.getTime());
	}

	public Date getExpirationDate() {
		return expirationDate == null ? null : new Date(
				expirationDate.getTime());
	}

	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate == null ? null : new Date(
				expirationDate.getTime());
	}

	/**
	 * Determines if this program status entry for this person is expired.
	 * 
	 * @return true if this program status entry for this person is expired;
	 *         false otherwise
	 */
	public boolean isExpired() {
		return expirationDate != null
				&& new Date().compareTo(expirationDate) >= 0;
	}

	@Override
	protected int hashPrime() {
		return 337;
	}

	@Override
	final public int hashCode() { // NOPMD
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonProgramStatus
		result *= hashField("person", person);
		result *= hashField("programStatus", programStatus);
		result *= hashField("programStatusChangeReason",
				programStatusChangeReason);
		result *= hashField("effectiveDate", effectiveDate);
		result *= hashField("expirationDate", expirationDate);

		return result;
	}
}