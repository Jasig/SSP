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

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.transferobject.TransferObject;

/**
 * ProgramStatus reference transfer objects
 * 
 * @author jon.adams
 */
public class ProgramStatusTO extends AbstractReferenceTO<ProgramStatus>
		implements TransferObject<ProgramStatus> {

	private boolean programStatusChangeReasonRequired;

	/**
	 * Empty constructor
	 */
	public ProgramStatusTO() {
		super();
	}

	/**
	 * Constructor to initialize the required properties of the class.
	 * 
	 * @param id
	 *            identifier
	 * @param name
	 *            name shown to the user
	 * @param programStatusChangeReasonRequired
	 *            The programStatusChangeReasonRequired tells the consumer that
	 *            when a student's program status is changed to this status then
	 *            a ProgramStatusChangeReason must be specified.
	 */
	public ProgramStatusTO(@NotNull final UUID id, @NotNull final String name,
			final boolean programStatusChangeReasonRequired) {
		super();
		super.setId(id);
		super.setName(name);
		this.programStatusChangeReasonRequired = programStatusChangeReasonRequired;
	}

	/**
	 * Constructor to initialize all properties of the class.
	 * 
	 * @param id
	 *            identifier
	 * @param name
	 *            name shown to the user
	 * @param description
	 *            description shown to the user in detail views
	 * @param programStatusChangeReasonRequired
	 *            The programStatusChangeReasonRequired tells the consumer that
	 *            when a student's program status is changed to this status then
	 *            a ProgramStatusChangeReason must be specified.
	 */
	public ProgramStatusTO(@NotNull final UUID id, @NotNull final String name,
			final String description,
			final boolean programStatusChangeReasonRequired) {
		super(id, name, description);
		this.programStatusChangeReasonRequired = programStatusChangeReasonRequired;
	}

	/**
	 * Construct a transfer object based on the data in the supplied model.
	 * 
	 * @param model
	 *            Model data to copy
	 */
	public ProgramStatusTO(@NotNull final ProgramStatus model) {
		super();

		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		from(model);
	}

	@Override
	public final void from(final ProgramStatus model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);
		programStatusChangeReasonRequired = model
				.isProgramStatusChangeReasonRequired();
	}

	/**
	 * @return if program status change reason is required
	 */
	public boolean isProgramStatusChangeReasonRequired() {
		return programStatusChangeReasonRequired;
	}

	/**
	 * @param programStatusChangeReasonRequired
	 *            The programStatusChangeReasonRequired tells the consumer that
	 *            when a student's program status is changed to this status then
	 *            a ProgramStatusChangeReason must be specified.
	 */
	public void setProgramStatusChangeReasonRequired(
			final boolean programStatusChangeReasonRequired) {
		this.programStatusChangeReasonRequired = programStatusChangeReasonRequired;
	}
}