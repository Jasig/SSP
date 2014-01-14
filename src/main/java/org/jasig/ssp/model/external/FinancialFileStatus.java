package org.jasig.ssp.model.external;

public enum FinancialFileStatus {
	/*
	 * WARNING: Only _append_ enum values to this list! Do not insert new values
	 * in between others or change the order, or existing database values will
	 * become out of sync because the Hibernate mapping uses ordinals (the
	 * integer index of the enum in this list) instead of keys (strings based on
	 * the name of the enum).
	 */
	
	INCOMPLETE,
	PENDING,
	COMPLETE
}
