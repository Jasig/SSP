package org.jasig.ssp.security.permissions;


public enum Role implements SspPermission {
	
	ACADEMIC_COUNSELOR_ADVISER("Role_Academic_Counselor_Advisor"),
	
	COUNSELOR("Role_Counselor"),
	
	FACULTY("Role_Faculty"),
	
	DEVELOPER("Role_Developer"),
	
	MANAGEMENT("Role_Management"),
	
	SUPER_USER("Role_Super_User"),
	
	SUPPORT_STAFF("Role_Support_Staff"),
	
	STUDENT("Role_Student"),
	
	ANONYMOUS("Role_Anonymous");
	
	/*
	 * Implementation
	 */
	
	/**
	 * Used with the portlet API to communicate with the portal.
	 */
	private final String roleName;
	
	private Role(final String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String getRoleName() {
		return roleName;
	}

}
