/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.model;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

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
	 * 
	 * <p>
	 * ObjectStatus should be set to {@link #INACTIVE} instead of
	 * {@link #DELETED}.
	 */
	DELETED,

	/**
	 * Object is marked as obsolete (integer: 4)
	 *
	 * <p>
	 * ObjectStatus should be set to {@link #INACTIVE} instead of
	 * {@link #DELETED}.
	 */
	OBSOLETE;;

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
}