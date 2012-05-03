package org.jasig.ssp.security;

public enum AccessType implements SspPermission {
	
	ARC("AccessType_ARC"),
	
	CNSL("AccessType_CNSL"),
	
	DIS("AccessType_DIS"),
	
	DSW("AccessType_DSW"),
	
	EAL("AccessType_EAL"),
	
	ESL("AccessType_ESL"),
	
	FFC("AccessType_FFC"),
	
	ILP("AccessType_ILP"),
	
	MGR("AccessType_MGR"),
	
	STAFF("AccessType_STAFF");
	
	/*
	 * Implementation
	 */
	
	/**
	 * Used with the portlet API to communicate with the portal.
	 */
	private final String roleName;
	
	private AccessType(final String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String getRoleName() {
		return roleName;
	}

}
