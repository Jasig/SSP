package org.jasig.ssp.security.permissions;

/**
 * It is simple to mistype in an annotation. This class is an attempt to give
 * slightly more compile time protection.
 */
public class Permission { // NOPMD enum won't work for these values in
							// Annotations

	//
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
}