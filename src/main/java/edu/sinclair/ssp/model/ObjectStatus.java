package edu.sinclair.ssp.model;

/**
 * Basis for soft delete functionality.
 * 
 * @author daniel.bower
 */
public enum ObjectStatus {
	/*
	 * WARNING: Only _append_ enum values to this list! Do not insert new values
	 * in between others or change the order, or existing database values will
	 * become out of sync because the Hibernate mapping uses ordinals (the
	 * integer index of the enum in this list) instead of keys (strings based on
	 * the name of the enum).
	 */

	/**
	 * ALL is used when filtering by this field, to allow all the ObjectStatus
	 * values to be included.
	 */
	ALL,

	/**
	 * Valid, active user or student. Has appropriate access for their security
	 * level.
	 */
	ACTIVE,

	/**
	 * Inactive account, so they may not authenticate into the system. However,
	 * any account in this status will still be shown in administrative views,
	 * with the purpose of allowing the account to be re-enabled in the future,
	 * or continue to be shown in references to any history for this user.
	 */
	INACTIVE,

	/**
	 * Indicates that the account should no longer be shown in any view. Not
	 * completely removed from the database, but should never be seen in any
	 * user or administrator view in the system or API.
	 */
	DELETED;
}
