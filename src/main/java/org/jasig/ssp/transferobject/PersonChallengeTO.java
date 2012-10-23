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

import org.jasig.ssp.model.PersonChallenge;

import com.google.common.collect.Lists;

public class PersonChallengeTO
		extends AbstractAuditableTO<PersonChallenge>
		implements TransferObject<PersonChallenge> {

	@NotNull
	private UUID challengeId;

	@NotNull
	private UUID personId;

	private String description;

	public PersonChallengeTO() {
		super();
	}

	public PersonChallengeTO(final PersonChallenge model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonChallenge model) {
		super.from(model);
		setDescription(model.getDescription());

		if (model.getChallenge() != null) {
			setChallengeId(model.getChallenge().getId());
		}

		if (model.getPerson() != null) {
			setPersonId(model.getPerson().getId());
		}
	}

	public static List<PersonChallengeTO> toTOList(
			final Collection<PersonChallenge> models) {
		final List<PersonChallengeTO> tos = Lists.newArrayList();
		for (final PersonChallenge model : models) {
			tos.add(new PersonChallengeTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public final void setChallengeId(final UUID challengeId) {
		this.challengeId = challengeId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public String getDescription() {
		return description;
	}

	public final void setDescription(final String description) {
		this.description = description;
	}
}