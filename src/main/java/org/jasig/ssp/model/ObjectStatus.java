package org.jasig.ssp.model;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Basis for soft delete functionality.
 * 
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

	public static <T extends Auditable> List<T> filterForStatus(
			Collection<T> list, ObjectStatus status) {
		List<T> inStatus = Lists.newArrayList();

		for (T t : list) {
			if (status.equals(t.getObjectStatus())) {
				inStatus.add(t);
			}
		}

		return inStatus;
	}
}
