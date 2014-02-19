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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.Strength;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;

import com.google.common.collect.Lists;

/**
 * Strength transfer object
 */
public class StrengthTO
		extends AbstractAuditableTO<Strength>
		implements TransferObject<Strength>, Serializable {

	private static final long serialVersionUID = 5011875522731047877L;

	@NotNull
	@NotEmpty
	private String name;

	private String description;

	private UUID personId;

	public ConfidentialityLevelLiteTO confidentialityLevel;

	/**
	 * Empty constructor
	 */
	public StrengthTO() {
		super();
	}

	/**
	 * Create a transfer object equivalent to the specified model
	 * 
	 * @param model
	 *            Model to copy
	 */
	public StrengthTO(final Strength model) {
		super();
		from(model);
	}

	@Override
	public final void from(final Strength model) {
		super.from(model);

		name = model.getName();
		description = model.getDescription();
		personId = model.getPerson() == null ? null
				: model.getPerson().getId();
		confidentialityLevel = ConfidentialityLevelLiteTO.fromModel(
				model.getConfidentialityLevel());
	}

	/**
	 * Converts a list of models to equivalent transfer objects.
	 * 
	 * @param models
	 *            model tasks to convert to equivalent transfer objects
	 * @return List of equivalent transfer objects, or empty List if null or
	 *         empty.
	 */
	public static List<StrengthTO> toTOList(
			final Collection<Strength> models) {
		final List<StrengthTO> tObjects = Lists.newArrayList();
		if (null != models) {
			for (final Strength model : models) {
				tObjects.add(new StrengthTO(model)); // NOPMD
			}
		}

		return tObjects;
	}

	/**
	 * Gets the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 *            the name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * 
	 * @param description
	 *            the description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets the associated Person identifier
	 * 
	 * @return the PersonId
	 */
	public UUID getPersonId() {
		return personId;
	}

	/**
	 * Sets the associated Person by identifier
	 * 
	 * @param personId
	 *            person id
	 */
	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	/**
	 * Gets the confidentiality level
	 * 
	 * @return the confidentiality level
	 */
	public ConfidentialityLevelLiteTO getConfidentialityLevel() {
		return confidentialityLevel;
	}

	/**
	 * Sets the confidentiality level
	 * 
	 * @param confidentialityLevel
	 *            the confidentiality level
	 */
	public void setConfidentialityLevel(
			final ConfidentialityLevelLiteTO confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}
}