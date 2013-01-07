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

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonDisabilityAccommodation;

import com.google.common.collect.Lists;

public class PersonDisabilityAccommodationTO
		extends AbstractAuditableTO<PersonDisabilityAccommodation>
		implements TransferObject<PersonDisabilityAccommodation> {

	@NotNull
	private UUID disabilityAccommodationId;

	@NotNull
	private UUID personId;

	private String description;

	public PersonDisabilityAccommodationTO() {
		super();
	}

	public PersonDisabilityAccommodationTO(final PersonDisabilityAccommodation model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonDisabilityAccommodation model) {
		super.from(model);

		description = model.getDescription();

		if ((model.getDisabilityAccommodation() != null)
				&& (model.getDisabilityAccommodation().getId() != null)) {
			disabilityAccommodationId = model.getDisabilityAccommodation().getId();
		}

		if ((model.getPerson() != null)
				&& (model.getPerson().getId() != null)) {
			personId = model.getPerson().getId();
		}
	}

	public static List<PersonDisabilityAccommodationTO> toTOList(
			final Collection<PersonDisabilityAccommodation> models) {
		final List<PersonDisabilityAccommodationTO> tos = Lists.newArrayList();
		for (final PersonDisabilityAccommodation model : models) {
			tos.add(new PersonDisabilityAccommodationTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getDisabilityAccommodationId() {
		return disabilityAccommodationId;
	}

	public void setDisabilityAccommodationId(final UUID disabilityAccommodationId) {
		this.disabilityAccommodationId = disabilityAccommodationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
