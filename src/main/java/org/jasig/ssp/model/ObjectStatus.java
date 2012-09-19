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
	ALL {
		@Override
		public ObjectStatus and(final ObjectStatus associationStatus) {
			if ( associationStatus == null || associationStatus == this ) {
				return this;
			}
			return associationStatus;
		}
	},

	/**
	 * Object is appropriate for use in creating/editing records. (integer: 1)
	 */
	ACTIVE {
		@Override
		public ObjectStatus and(final ObjectStatus associationStatus) {
			if ( associationStatus == null || associationStatus == this ||
					associationStatus == ALL ) {
				return this;
			}
			return associationStatus;
		}
	},

	/**
	 * Object is not appropriate for use in creating/editing records. (integer:
	 * 2)
	 */
	INACTIVE {
		@Override
		public ObjectStatus and(final ObjectStatus associationStatus) {
			return this;
		}
	},

	/**
	 * Object has been removed, and should not be used in creating/editing
	 * records (integer: 3)
	 * 
	 * <p>
	 * ObjectStatus should be set to {@link #INACTIVE} instead of
	 * {@link #DELETED}.
	 */
	@Deprecated
	DELETED {
		@Override
		public ObjectStatus and(final ObjectStatus associationStatus) {
			return this;
		}
	};

	/**
	 * Filter a collection to return only those that have the specified status.
	 * 
	 * @param list
	 *            List of {@link AbstractAuditable}
	 * @param status
	 *            Only return instances that match this status
	 * @return A list of instances that match this status
	 */
	public static <T extends Auditable> List<T> filterForStatus(
			final Collection<T> list, final ObjectStatus status) {
		final List<T> inStatus = Lists.newArrayList();

		for (final T t : list) {
			if (status.equals(ObjectStatus.ALL)
					|| status.equals(t.getObjectStatus())) {
				inStatus.add(t);
			}
		}

		return inStatus;
	}

	/**
	 * Quasi-bitwise 'and' of two ObjectStatuses. Used to calculate a combined
	 * status of two associated but independently statused objects. Basically,
	 * both objects must be in a non-deleted/inactive state to be considered
	 * active as a pair.
	 *
	 * @param associationStatus the status of the paired object. nulls are
	 *                          result in the current ObjectStatus
	 * @return
	 */
	public abstract ObjectStatus and(ObjectStatus associationStatus);
}