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
package org.jasig.ssp.model.reference;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;

/**
 * ProgramStatus reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProgramStatus extends AbstractReference implements Auditable {
	


	private static final long serialVersionUID = -6549195550826087907L;

	public static final UUID ACTIVE_ID = UUID
			.fromString("b2d12527-5056-a51a-8054-113116baab88");

	public static final UUID INACTIVE_ID = UUID
			.fromString("b2d125a4-5056-a51a-8042-d50b8eff0df1");

	public static final UUID TRANSITIONED_ID = UUID
			.fromString("b2d125e3-5056-a51a-800f-6891bc7d1ddc");

	public static final UUID NON_PARTICIPATING_ID = UUID
			.fromString("b2d125c3-5056-a51a-8004-f1dbabde80c2");

	public static final UUID NO_SHOW = UUID
			.fromString("b2d12640-5056-a51a-80cc-91264965731a");
	
	public static final Map<String,String> PROGRAM_STATUS_CODES  = new Hashtable<String,String>();
	static
    {
		PROGRAM_STATUS_CODES.put(ACTIVE_ID.toString(), "A");
		PROGRAM_STATUS_CODES.put(INACTIVE_ID.toString(), "IA");
		PROGRAM_STATUS_CODES.put(NON_PARTICIPATING_ID.toString(), "NP");
		PROGRAM_STATUS_CODES.put(NO_SHOW.toString(), "NS");
		PROGRAM_STATUS_CODES.put(TRANSITIONED_ID.toString(), "T");
    }

	@Column(nullable = false)
	@NotNull
	private boolean programStatusChangeReasonRequired = false;

	/**
	 * Constructor
	 */
	public ProgramStatus() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public ProgramStatus(@NotNull final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 * @param description
	 *            Description; max 64000 characters
	 * @param programStatusChangeReasonRequired
	 *            The programStatusChangeReasonRequired tells the consumer that
	 *            when a student's program status is changed to this status then
	 *            a ProgramStatusChangeReason must be specified.
	 */
	public ProgramStatus(@NotNull final UUID id, @NotNull final String name,
			final String description,
			final boolean programStatusChangeReasonRequired) {
		super(id, name, description);
		this.programStatusChangeReasonRequired = programStatusChangeReasonRequired;
	}

	/**
	 * @return if program status change reason is required
	 */
	public boolean isProgramStatusChangeReasonRequired() {
		return programStatusChangeReasonRequired;
	}

	/**
	 * Sets the programStatusChangeReasonRequired
	 * 
	 * @param programStatusChangeReasonRequired
	 *            The programStatusChangeReasonRequired tells the consumer that
	 *            when a student's program status is changed to this status then
	 *            a ProgramStatusChangeReason must be specified.
	 */
	public void setProgramStatusChangeReasonRequired(
			final boolean programStatusChangeReasonRequired) {
		this.programStatusChangeReasonRequired = programStatusChangeReasonRequired;
	}

	/**
	 * Unique (amongst all Models in the system) prime for use by
	 * {@link #hashCode()}
	 */
	@Override
	protected int hashPrime() {
		return 331;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams
		return hashPrime() * super.hashCode()
				* (programStatusChangeReasonRequired ? 3 : 5);
	}

	@Override
	public String toString() {
		return "Program Status: \"" + super.getName() + " (" + super.toString()
				+ ")";
	}
}