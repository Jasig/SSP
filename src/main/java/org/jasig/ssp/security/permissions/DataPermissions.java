package org.jasig.ssp.security.permissions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * These are the data permissions in SSP.
 * 
 * @author awills
 */
public enum DataPermissions {
	/**
	 * Not Confidential. Records may be viewed by all users.
	 */
	DATA_EVERYONE,

	DATA_ACADEMIC_RESOURCE_CENTER,

	DATA_COUNSELING_SERVICES,

	DATA_DISABILITY,

	DATA_DISPLACED_WORKERS,

	DATA_EARLY_ALERT,

	DATA_ENGLISH_SECOND_LANGUAGE,

	DATA_FAST_FORWARD,

	DATA_INDIVIDUALIZED_LEARNING_PLAN,

	DATA_MANAGER,

	DATA_STAFF,

	/**
	 * Suitable for testing
	 */
	UNKNOWN;

	/**
	 * Converts a data permission to a format that can be used as a Permission
	 * string.
	 * 
	 * @return The data permission in Permission string format.
	 */
	public String asPermissionString() {
		return "ROLE_" + this;
	}

	/**
	 * Creates a GrantedAuthority for this data permission
	 * 
	 * @return A GrantedAuthority equivalent of this data permission
	 */
	public GrantedAuthority asGrantedAuthority() {
		return new GrantedAuthorityImpl(asPermissionString());
	}
}