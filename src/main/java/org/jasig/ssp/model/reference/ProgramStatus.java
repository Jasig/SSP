package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;

/**
 * ProgramStatus reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProgramStatus extends AbstractReference implements Auditable {

	private static final long serialVersionUID = -6549195550826087907L;

	public static final UUID ACTIVE_ID = UUID
			.fromString("b2d12527-5056-a51a-8054-113116baab88");

	@Column(nullable = false)
	@NotNull
	private boolean programStatusChangeReasonRequired = false;

	/**
	 * Constructor
	 */
	public ProgramStatus() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public ProgramStatus(@NotNull final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 * @param description
	 *            Description; max 64000 characters
	 * @param programStatusChangeReasonRequired
	 *            The programStatusChangeReasonRequired tells the consumer that
	 *            when a student's program status is changed to this status then
	 *            a ProgramStatusChangeReason must be specified.
	 */
	public ProgramStatus(@NotNull final UUID id, @NotNull final String name,
			final String description,
			final boolean programStatusChangeReasonRequired) {
		super(id, name, description);
		this.programStatusChangeReasonRequired = programStatusChangeReasonRequired;
	}

	/**
	 * @return if program status change reason is required
	 */
	public boolean isProgramStatusChangeReasonRequired() {
		return programStatusChangeReasonRequired;
	}

	/**
	 * Sets the programStatusChangeReasonRequired
	 * 
	 * @param programStatusChangeReasonRequired
	 *            The programStatusChangeReasonRequired tells the consumer that
	 *            when a student's program status is changed to this status then
	 *            a ProgramStatusChangeReason must be specified.
	 */
	public void setProgramStatusChangeReasonRequired(
			final boolean programStatusChangeReasonRequired) {
		this.programStatusChangeReasonRequired = programStatusChangeReasonRequired;
	}

	/**
	 * Unique (amongst all Models in the system) prime for use by
	 * {@link #hashCode()}
	 */
	@Override
	protected int hashPrime() {
		return 331;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams
		return hashPrime() * super.hashCode()
				* (programStatusChangeReasonRequired ? 3 : 5);
	}
}
