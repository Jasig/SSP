package org.studentsuccessplan.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.studentsuccessplan.ssp.model.Auditable;

/**
 * Reference entities must all share this abstract class, so they inherit the
 * identifier, name, and description properties.
 */
@MappedSuperclass
public abstract class AbstractReference extends Auditable {

	/**
	 * Name
	 * 
	 * Required, not null, max length 80 characters.
	 */
	@Column(name = "name", nullable = false, length = 80)
	@NotNull
	@NotEmpty
	@Size(max = 80)
	private String name;

	/**
	 * Description
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String description;

	/**
	 * Constructor
	 */
	public AbstractReference() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */
	public AbstractReference(@NotNull final UUID id) {
		setId(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 */
	public AbstractReference(@NotNull final UUID id, @NotNull final String name) {
		setId(id);
		this.name = name;
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
	 */
	public AbstractReference(@NotNull final UUID id,
			@NotNull final String name, final String description) {
		setId(id);
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 *            Name; required; max 80 characters
	 */
	public void setName(@NotNull final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * 
	 * @param description
	 *            Name; null allowed; max 80 characters
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Overwrites properties with the source object's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 */
	public void overwrite(@NotNull final AbstractReference source) {
		setName(source.getName());
		setDescription(source.getDescription());
	}

	@Override
	public int hashCode() {
		int result = hashPrime();

		// Auditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// AbstractReference
		result *= name == null ? "name".hashCode() : name.hashCode();
		result *= description == null ? "description".hashCode() : description
				.hashCode();

		return result;
	}
}
