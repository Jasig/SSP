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
	 * Object is appropriate for use in creating/editing records.
	 */
	ACTIVE,

	/**
	 * Object is not appropriate for use in creating/editing records.
	 */
	INACTIVE,

	/**
	 * Object has been removed, and should not be used in creating/editing
	 * records
	 */
	DELETED;
}
