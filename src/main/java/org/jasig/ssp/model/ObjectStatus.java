package org.jasig.ssp.model;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Enumeration for soft delete functionality.
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
	 * Object is appropriate for use in creating/editing records. (integer: 1)
	 */
	ACTIVE,

	/**
	 * Object is not appropriate for use in creating/editing records. (integer:
	 * 2)
	 */
	INACTIVE,

	/**
	 * Object has been removed, and should not be used in creating/editing
	 * records (integer: 3)
	 */
	DELETED;

	/**
	 * Filter a collection to return only those that have the specified status.
	 * 
	 * @param list
	 *            List of {@link Auditable}
	 * @param status
	 *            Only return instances that match this status
	 * @return A list of instances that match this status
	 */
	public static <T extends Auditable> List<T> filterForStatus(
			Collection<T> list, ObjectStatus status) {
		List<T> inStatus = Lists.newArrayList();

		for (T t : list) {
			if (status.equals(ObjectStatus.ALL)
					|| status.equals(t.getObjectStatus())) {
				inStatus.add(t);
			}
		}

		return inStatus;
	}
}
