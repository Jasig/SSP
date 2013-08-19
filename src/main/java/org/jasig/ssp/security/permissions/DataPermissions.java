/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.security.permissions;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jasig.ssp.transferobject.PermissionTO;
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
	
	DATA_TEST,


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

	public static final List<PermissionTO> PERMISSION_TRANSFER_OBJECTS
			= Collections.unmodifiableList(new LinkedList<PermissionTO>() {{
		final DataPermissions[] values = DataPermissions.values();
		for (DataPermissions value : values) {
			add(new PermissionTO(value.name(), value.asPermissionString()));
		}
	}});

}