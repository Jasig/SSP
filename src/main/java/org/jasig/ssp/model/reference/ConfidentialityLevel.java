package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.security.permissions.DataPermissions;

/**
 * ConfidentialityLevel reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConfidentialityLevel
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 2346103896744918201L;

	public static final UUID CONFIDENTIALITYLEVEL_EVERYONE = UUID
			.fromString("afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c");

	@Column(nullable = false, length = 10)
	@NotNull
	@NotEmpty
	@Size(max = 10)
	private String acronym;

	@Column(nullable = false, unique = true)
	@NotNull
	@Enumerated(EnumType.STRING)
	private DataPermissions permission;

	/**
	 * Constructor
	 */
	public ConfidentialityLevel() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */
	public ConfidentialityLevel(final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 */
	public ConfidentialityLevel(@NotNull final UUID id,
			@NotNull final String name) {
		super(id, name);
	}

	public ConfidentialityLevel(@NotNull final UUID id,
			@NotNull final String name,
			@NotNull final String acronym,
			@NotNull final DataPermissions roleId) {
		super(id, name);
		this.acronym = acronym;
		this.permission = roleId;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}

	public DataPermissions getPermission() {
		return permission;
	}

	public void setPermission(final DataPermissions permission) {
		this.permission = permission;
	}

	@Override
	protected int hashPrime() {
		return 79;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime() * super.hashCode();

		result *= hashField("acronym", acronym);
		result *= permission == null ? "permission".hashCode() : permission
				.hashCode();

		return result;
	}
}