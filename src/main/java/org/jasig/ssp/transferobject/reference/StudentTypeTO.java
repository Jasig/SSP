/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * StudentType transfer object
 * 
 * @author jon.adams
 * 
 */
public class StudentTypeTO
		extends AbstractReferenceTO<StudentType>
		implements TransferObject<StudentType> { // NOPMD

	private boolean requireInitialAppointment = false;
	private String code;

	/**
	 * Empty constructor
	 */
	public StudentTypeTO() {
		super();
	}

	public StudentTypeTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public StudentTypeTO(final StudentType model) {
		super();
		from(model);
	}

	@Override
	public final void from(final StudentType model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);

		requireInitialAppointment = model.isRequireInitialAppointment();
		code = model.isCode();		
	}

	/**
	 * @return the requireInitialAppointment
	 */
	public boolean isRequireInitialAppointment() {
		return requireInitialAppointment;
	}

	/**
	 * @param requireInitialAppointment
	 *            the requireInitialAppointment to set
	 */
	public void setRequireInitialAppointment(
			final boolean requireInitialAppointment) {
		this.requireInitialAppointment = requireInitialAppointment;
	}

	/**
	 * @return the student type code
	 */
	public String isCode() {
		return code;
	}
	
	/**
	 * @param studentTypeCode
	 * 				the student type code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}
	
	public static List<StudentTypeTO> toTOList(
			final Collection<StudentType> models) {
		final List<StudentTypeTO> tObjects = Lists.newArrayList();
		for (final StudentType model : models) {
			tObjects.add(new StudentTypeTO(model)); // NOPMD
		}

		return tObjects;
	}
}