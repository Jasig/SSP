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

import org.jasig.ssp.model.PersonServiceReason;

import com.google.common.collect.Lists;

public class PersonServiceReasonTO
		extends AbstractAuditableTO<PersonServiceReason>
		implements TransferObject<PersonServiceReason> {

	@NotNull
	private UUID serviceReasonId;

	@NotNull
	private UUID personId;

	public PersonServiceReasonTO() {
		super();
	}

	public PersonServiceReasonTO(final PersonServiceReason model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonServiceReason model) {
		super.from(model);

		if (model.getServiceReason() != null) {
			setServiceReasonId(model.getServiceReason().getId());
		}

		if (model.getPerson() != null) {
			setPersonId(model.getPerson().getId());
		}
	}

	public static List<PersonServiceReasonTO> toTOList(
			final Collection<PersonServiceReason> models) {
		final List<PersonServiceReasonTO> tos = Lists.newArrayList();
		for (final PersonServiceReason model : models) {
			tos.add(new PersonServiceReasonTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getServiceReasonId() {
		return serviceReasonId;
	}

	public final void setServiceReasonId(final UUID serviceReasonId) {
		this.serviceReasonId = serviceReasonId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

}
