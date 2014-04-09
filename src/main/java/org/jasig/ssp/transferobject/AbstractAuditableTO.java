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
package org.jasig.ssp.transferobject;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transfer object for copy to and from equivalent Auditable models.
 * 
 * @param <T>
 *            Any {@link Auditable} model type.
 */
@JsonIgnoreProperties(ignoreUnknown = true) 
public abstract class AbstractAuditableTO<T extends Auditable>
		implements TransferObject<T> {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractAuditableTO.class);

	/**
	 * Empty constructor.
	 */
	public AbstractAuditableTO() {
		super();
	}

	/**
	 * Construct a simple Auditable with the specified identifier.
	 * 
	 * @param id
	 *            Identifier
	 */
	public AbstractAuditableTO(final UUID id) {
		super();
		this.id = id;
	}

	private UUID id;

	private Date createdDate;

	private PersonLiteTO createdBy;
	
	private Date modifiedDate;

	private PersonLiteTO modifiedBy;

	private ObjectStatus objectStatus;

	/**
	 * Get the identifier
	 * 
	 * @return the identifier
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Sets the identifier
	 * 
	 * @param id
	 *            the identifier; required
	 */
	public void setId(@NotNull final UUID id) {
		this.id = id;
	}

	/**
	 * Get the created date
	 * 
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}

	/**
	 * Sets the created date
	 * 
	 * @param createdDate
	 *            the created date; required
	 */
	public void setCreatedDate(@NotNull final Date createdDate) {
		this.createdDate = createdDate == null ? null : new Date(
				createdDate.getTime());
	}

	public PersonLiteTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(@NotNull final PersonLiteTO createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate == null ? null : new Date(modifiedDate.getTime());
	}

	public void setModifiedDate(@NotNull final Date modifiedDate) {
		this.modifiedDate = modifiedDate == null ? null : new Date(
				modifiedDate.getTime());
	}

	public PersonLiteTO getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(@NotNull final PersonLiteTO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(@NotNull final ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

	@Override
	public void from(final T model) {
		id = model.getId();
		if (model.getCreatedBy() != null) {
			createdBy = new PersonLiteTO(model.getCreatedBy().getId(),model.getCreatedBy().getFirstName(),model.getCreatedBy().getLastName());
		}
		
		if (model.getModifiedBy() != null) {
			modifiedBy = new PersonLiteTO(model.getModifiedBy().getId(),model.getModifiedBy().getFirstName(),model.getModifiedBy().getLastName());
		}

		createdDate = model.getCreatedDate();
		modifiedDate = model.getModifiedDate();
		objectStatus = model.getObjectStatus();
		
	}
}