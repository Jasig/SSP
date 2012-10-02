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

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.util.uuid.UUIDCustomType;

/**
 * Campus reference object.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@TypeDef(name = "uuid-custom", typeClass = UUIDCustomType.class)
public class Campus extends AbstractReference implements Auditable {

	private static final long serialVersionUID = -6346942820506585713L;

	@Column(nullable = false)
	@Type(type = "uuid-custom")
	@NotNull
	private UUID earlyAlertCoordinatorId;

	/**
	 * Constructor
	 */
	public Campus() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Campus(@NotNull final UUID id) {
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
	 * @param earlyAlertCoordinatorId
	 *            Early Alert Coordinator
	 */
	public Campus(@NotNull final UUID id, @NotNull final String name,
			final String description,
			@NotNull final UUID earlyAlertCoordinatorId) {
		super(id, name, description);
		this.earlyAlertCoordinatorId = earlyAlertCoordinatorId;
	}

	/**
	 * @return the earlyAlertCoordinatorId
	 */
	public UUID getEarlyAlertCoordinatorId() {
		return earlyAlertCoordinatorId;
	}

	/**
	 * @param earlyAlertCoordinatorId
	 *            the EarlyAlertCoordinatorId to set
	 */
	public void setEarlyAlertCoordinatorId(
			@NotNull final UUID earlyAlertCoordinatorId) {
		this.earlyAlertCoordinatorId = earlyAlertCoordinatorId;
	}

	/**
	 * Unique (amongst all Models in the system) prime for use by
	 * {@link #hashCode()}
	 */
	@Override
	protected int hashPrime() {
		return 239;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		return hashPrime() * super.hashCode()
				* hashField("earlyAlertCoordinatorId", earlyAlertCoordinatorId);
	}
}