package org.jasig.ssp.model.security;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * Derived with only minor changes from {@code org.springframework.security.core.authority.SimpleGrantedAuthority}
 */
@Embeddable
public class PersistentGrantedAuthority implements GrantedAuthority {

	@NotNull
	@NotEmpty
	@Column(nullable = false, length = 255) // matches up_permission.activity
	private String role;

	// for Hibernate
	public PersistentGrantedAuthority() {
		// nothing to do
	}

	// for app
	public PersistentGrantedAuthority(String role) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
	}

	public String getAuthority() {
		return role;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof GrantedAuthority) {
			return getAuthority().equals(((GrantedAuthority) obj).getAuthority());
		}

		return false;
	}

	public int hashCode() {
		return this.role.hashCode();
	}

	public String toString() {
		return this.role;
	}
}
