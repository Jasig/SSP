/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.security.permissions;

import org.apache.commons.collections.ListUtils;
import org.jasig.ssp.transferobject.PermissionTO;
import java.util.Collections;
import java.util.List;


/**
 * It is simple to mistype in an annotation. This class is an attempt to give
 * slightly more compile time protection.
 */
public class Permission { // NOPMD enum won't work for these values in
							// annotations

	private static final String HAS_ROLE = "hasRole('";
	private static final String HAS_ANY_ROLE = "hasAnyRole('";
	private static final String HAS_ANY_ROLE_SEPARATOR = "','";
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
	public static final String REFERENCE_WRITE = ROLE_PREFIX + "REFERENCE_WRITE";
	public static final String REFERENCE_ACCOMMODATION_WRITE = ROLE_PREFIX + "REFERENCE_ACCOMMODATION_WRITE";
	public static final String REFERENCE_CASELOAD_ASSIGNMENT_WRITE = ROLE_PREFIX + "REFERENCE_CASELOAD_ASSIGNMENT_WRITE";
	public static final String REFERENCE_COUNSELING_REF_GUIDE_WRITE = ROLE_PREFIX + "REFERENCE_COUNSELING_REF_GUIDE_WRITE";
	public static final String REFERENCE_EARLY_ALERT_WRITE = ROLE_PREFIX + "REFERENCE_EARLY_ALERT_WRITE";
	public static final String REFERENCE_JOURNAL_WRITE = ROLE_PREFIX + "REFERENCE_JOURNAL_WRITE";
	public static final String REFERENCE_MAIN_TOOL_WRITE = ROLE_PREFIX + "REFERENCE_MAIN_TOOL_WRITE";
	public static final String REFERENCE_EXTERNAL_VIEW_TOOL_WRITE = ROLE_PREFIX + "REFERENCE_EXTERNAL_VIEW_TOOL_WRITE";
	public static final String REFERENCE_MAP_WRITE = ROLE_PREFIX + "REFERENCE_MAP_WRITE";
	public static final String REFERENCE_MYGPS_WRITE = ROLE_PREFIX + "REFERENCE_MYGPS_WRITE";
	public static final String REFERENCE_SECURITY_WRITE = ROLE_PREFIX + "REFERENCE_SECURITY_WRITE";
	public static final String REFERENCE_STUDENT_INTAKE_WRITE = ROLE_PREFIX + "REFERENCE_STUDENT_INTAKE_WRITE";
	public static final String REFERENCE_SYSTEM_CONFIG_WRITE = ROLE_PREFIX + "REFERENCE_SYSTEM_CONFIG_WRITE";

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

	//Caseload Bulk Reassign
	public static final String PERSON_BULK_REASSIGN = ROLE_PREFIX + "PERSON_BULK_REASSIGN";

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
	public static final String SECURITY_PERSON_BULK_REASSIGN = HAS_ROLE + PERSON_BULK_REASSIGN
			+ END;
	public static final String SECURITY_REPORT_READ = HAS_ROLE + REPORT_READ
			+ END;

	public static final String SECURITY_API_KEY_READ = HAS_ROLE + API_KEY_READ
			+ END;
	public static final String SECURITY_API_KEY_WRITE = HAS_ROLE + API_KEY_WRITE
			+ END;
	public static final String SECURITY_REFERENCE_ACCOMMODATION_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_ACCOMMODATION_WRITE + END;
	public static final String SECURITY_REFERENCE_CASELOAD_ASSIGNMENT_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_CASELOAD_ASSIGNMENT_WRITE + END;
	public static final String SECURITY_REFERENCE_COUNSELING_REF_GUIDE_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_COUNSELING_REF_GUIDE_WRITE + END;
	public static final String SECURITY_REFERENCE_EARLY_ALERT_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_EARLY_ALERT_WRITE + END;
	public static final String SECURITY_REFERENCE_JOURNAL_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_JOURNAL_WRITE + END;
	public static final String SECURITY_REFERENCE_MAIN_TOOL_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_MAIN_TOOL_WRITE + END;
    public static final String SECURITY_REFERENCE_EXTERNAL_VIEW_TOOL_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_EXTERNAL_VIEW_TOOL_WRITE + END;
    public static final String SECURITY_REFERENCE_MAP_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_MAP_WRITE + END;
	public static final String SECURITY_REFERENCE_MYGPS_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_MYGPS_WRITE + END;
	public static final String SECURITY_REFERENCE_SECURITY_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_SECURITY_WRITE + END;
	public static final String SECURITY_REFERENCE_STUDENT_INTAKE_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_STUDENT_INTAKE_WRITE + END;
	public static final String SECURITY_REFERENCE_SYSTEM_CONFIG_WRITE = HAS_ANY_ROLE + REFERENCE_WRITE + HAS_ANY_ROLE_SEPARATOR + REFERENCE_SYSTEM_CONFIG_WRITE + END;

	public static final List<PermissionTO> PERMISSION_TRANSFER_OBJECTS =
			Collections.unmodifiableList(ListUtils.union(
					DataPermissions.PERMISSION_TRANSFER_OBJECTS,
					ServicePermissions.PERMISSION_TRANSFER_OBJECTS));

}