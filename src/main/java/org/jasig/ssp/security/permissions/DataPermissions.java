package org.jasig.ssp.security.permissions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * These are the data permissions in SSP.
 * 
 * @author awills
 */
public enum DataPermissions {
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
	UNKNOWN; // suitable for testing

	public String asPermissionString() {
		return "ROLE_" + this;
	}

	public GrantedAuthority asGrantedAuthority() {
		return new GrantedAuthorityImpl(asPermissionString());
	}

}
