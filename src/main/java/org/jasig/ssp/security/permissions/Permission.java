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
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.jasig.ssp.transferobject.PermissionTO;

/**
 * It is simple to mistype in an annotation. This class is an attempt to give
 * slightly more compile time protection.
 */
public class Permission { // NOPMD enum won't work for these values in
							// annotations

	private static final String HAS_ROLE = "hasRole('";
	private static final String END = "')";
	private static final String ROLE_PREFIX = "ROLE_";

	// Spring Security functions
	public static final String PERMIT_ALL = "permitAll";
	public static final String DENY_ALL = "denyAll";
	public static final String IS_ANONYMOUS = "isAnonymous()";
	public static final String IS_REMEMBER_ME = "isRememberMe()";
	public static final String IS_AUTHENTICATED = "isAuthenticated()";

	// Reference Permissions/Roles
	public static final String REFERENCE_READ = ROLE_PREFIX + "REFERENCE_READ";
	public static final String REFERENCE_WRITE = ROLE_PREFIX
			+ "REFERENCE_WRITE";

	// Person Permissions/Roles
	public static final String PERSON_READ = ROLE_PREFIX + "PERSON_READ";
	public static final String PERSON_WRITE = ROLE_PREFIX + "PERSON_WRITE";
	public static final String PERSON_DELETE = ROLE_PREFIX + "PERSON_DELETE";
	public static final String PERSON_INSTRUCTION_READ = ROLE_PREFIX
			+ "PERSON_INSTRUCTION_READ";
	public static final String PERSON_PROGRAM_STATUS_READ = ROLE_PREFIX
			+ "PERSON_PROGRAM_STATUS_READ";
	public static final String PERSON_PROGRAM_STATUS_WRITE = ROLE_PREFIX
			+ "PERSON_PROGRAM_STATUS_WRITE";

	// Report Permissions/Roles
	public static final String REPORT_READ = ROLE_PREFIX + "REPORT_READ";

	// API Key Permissions/Roles
	public static final String API_KEY_READ = ROLE_PREFIX + "API_KEY_READ";
	public static final String API_KEY_WRITE = ROLE_PREFIX + "API_KEY_WRITE";

	// Wrap the permission role in a hasRole
	public static final String SECURITY_REFERENCE_WRITE = HAS_ROLE
			+ REFERENCE_WRITE + END;

	public static final String SECURITY_REFERENCE_READ = HAS_ROLE
			+ REFERENCE_READ + END;

	public static final String SECURITY_PERSON_READ = HAS_ROLE + PERSON_READ
			+ END;
	public static final String SECURITY_PERSON_WRITE = HAS_ROLE + PERSON_WRITE
			+ END;
	public static final String SECURITY_PERSON_DELETE = HAS_ROLE
			+ PERSON_DELETE + END;
	public static final String SECURITY_PERSON_INSTRUCTION_READ = HAS_ROLE
			+ PERSON_INSTRUCTION_READ + END;

	public static final String SECURITY_REPORT_READ = HAS_ROLE + REPORT_READ
			+ END;

	public static final String SECURITY_API_KEY_READ = HAS_ROLE + API_KEY_READ
			+ END;
	public static final String SECURITY_API_KEY_WRITE = HAS_ROLE + API_KEY_WRITE
			+ END;


	public static final List<PermissionTO> PERMISSION_TRANSFER_OBJECTS =
			Collections.unmodifiableList(ListUtils.union(
					DataPermissions.PERMISSION_TRANSFER_OBJECTS,
					ServicePermissions.PERMISSION_TRANSFER_OBJECTS));

}