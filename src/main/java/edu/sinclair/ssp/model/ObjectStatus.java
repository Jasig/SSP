package edu.sinclair.ssp.model;

/**
 * Basis for soft delete functionality
 * 
 * @author daniel
 * 
 */
public enum ObjectStatus {
	/*
	 * BEWARE: Only append enum values to this list; do not change the order or
	 * existing database values will become out of sync because the Hibernate
	 * mapping uses ordinals instead of strings.
	 */
	ALL, ACTIVE, INACTIVE, DELETED;
}
