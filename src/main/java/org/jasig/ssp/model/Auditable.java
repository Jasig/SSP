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
package org.jasig.ssp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * Base for all models in the system that includes the ID, current
 * {@link ObjectStatus}, and date and person for the model instance creator and
 * last modifier.
 */
public interface Auditable extends Serializable {

	/**
	 * Gets the ID
	 * 
	 * @return the ID
	 */
	UUID getId();

	void setId(@NotNull final UUID id);

	/**
	 * Gets the created date
	 * 
	 * @return the created date
	 */
	Date getCreatedDate();

	void setCreatedDate(@NotNull final Date createdDate);

	AuditPerson getCreatedBy();

	void setCreatedBy(@NotNull final AuditPerson createdBy);

	/**
	 * Gets the modified date
	 * 
	 * @return the modified date
	 */
	Date getModifiedDate();

	void setModifiedDate(final Date modifiedDate);

	AuditPerson getModifiedBy();

	void setModifiedBy(final AuditPerson modifiedBy);

	/**
	 * Gets the {@link ObjectStatus}
	 * 
	 * @return the current ObjectStatus
	 */
	ObjectStatus getObjectStatus();

	void setObjectStatus(@NotNull final ObjectStatus objectStatus);

	/**
	 * Returns true if the object has been changed but not yet persisted to
	 * storage.
	 * 
	 * @return True if the object has been changed but not yet persisted to
	 *         storage.
	 */
	boolean isTransient();

}