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

import org.jasig.ssp.model.PersonFundingSource;

import com.google.common.collect.Lists;

public class PersonFundingSourceTO
		extends AbstractAuditableTO<PersonFundingSource>
		implements TransferObject<PersonFundingSource> {

	@NotNull
	private UUID fundingSourceId;

	@NotNull
	private UUID personId;

	private String description;

	public PersonFundingSourceTO() {
		super();
	}

	public PersonFundingSourceTO(final PersonFundingSource model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonFundingSource model) {
		super.from(model);

		description = model.getDescription();

		if ((model.getFundingSource() != null)
				&& (model.getFundingSource().getId() != null)) {
			fundingSourceId = model.getFundingSource().getId();
		}

		if ((model.getPerson() != null)
				&& (model.getPerson().getId() != null)) {
			personId = model.getPerson().getId();
		}
	}

	public static List<PersonFundingSourceTO> toTOList(
			final Collection<PersonFundingSource> models) {
		final List<PersonFundingSourceTO> tos = Lists.newArrayList();
		for (final PersonFundingSource model : models) {
			tos.add(new PersonFundingSourceTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getFundingSourceId() {
		return fundingSourceId;
	}

	public void setFundingSourceId(final UUID fundingSourceId) {
		this.fundingSourceId = fundingSourceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}