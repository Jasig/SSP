package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.jasig.ssp.model.Auditable;

/**
 * Campus reference object.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Campus extends AbstractReference implements Auditable {

	private static final long serialVersionUID = -6346942820506585713L;

	@Column(nullable = false)
	@Type(type = "pg-uuid")
	@NotNull
	private UUID earlyAlertCoordinatorId;

	/**
	 * Constructor
	 */
	public Campus() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Campus(@NotNull final UUID id) {
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
	 * @param earlyAlertCoordinatorId
	 *            Early Alert Coordinator
	 */
	public Campus(@NotNull final UUID id, @NotNull final String name,
			final String description,
			@NotNull final UUID earlyAlertCoordinatorId) {
		super(id, name, description);
		this.earlyAlertCoordinatorId = earlyAlertCoordinatorId;
	}

	/**
	 * @return the earlyAlertCoordinatorId
	 */
	public UUID getEarlyAlertCoordinatorId() {
		return earlyAlertCoordinatorId;
	}

	/**
	 * @param earlyAlertCoordinatorId
	 *            the EarlyAlertCoordinatorId to set
	 */
	public void setEarlyAlertCoordinatorId(
			@NotNull final UUID earlyAlertCoordinatorId) {
		if (earlyAlertCoordinatorId == null) {
			throw new IllegalArgumentException(
					"EarlyAlertCoordinatorId can not be null.");
		}

		this.earlyAlertCoordinatorId = earlyAlertCoordinatorId;
	}

	/**
	 * Unique (amongst all Models in the system) prime for use by
	 * {@link #hashCode()}
	 */
	@Override
	protected int hashPrime() {
		return 239;
	};

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		return hashPrime() * super.hashCode()
				* hashField("earlyAlertCoordinatorId", earlyAlertCoordinatorId);
	}
}