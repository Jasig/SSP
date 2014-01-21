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

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.model.PersonCompletedItem;

import com.google.common.collect.Lists;

public class PersonCompletedItemTO
		extends AbstractAuditableTO<PersonCompletedItem>
		implements TransferObject<PersonCompletedItem> {

	@NotNull
	private UUID completedItemId;

	@NotNull
	private UUID personId;

	public PersonCompletedItemTO() {
		super();
	}

	public PersonCompletedItemTO(final PersonCompletedItem model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonCompletedItem model) {
		super.from(model);

		if (model.getCompletedItem() != null) {
			setCompletedItemId(model.getCompletedItem().getId());
		}
		if (model.getPerson() != null) {
			setPersonId(model.getPerson().getId());
		}
	}

	public static List<PersonCompletedItemTO> toTOList(
			final Collection<PersonCompletedItem> models) {
		final List<PersonCompletedItemTO> tos = Lists.newArrayList();
		for (final PersonCompletedItem model : models) {
			if(ObjectStatus.ACTIVE.equals(model.getObjectStatus()))
			{
				tos.add(new PersonCompletedItemTO(model)); // NOPMD
			}
		}

		return tos;
	}


	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getCompletedItemId() {
		return completedItemId;
	}

	public void setCompletedItemId(UUID completedItemsId) {
		this.completedItemId = completedItemsId;
	}
}