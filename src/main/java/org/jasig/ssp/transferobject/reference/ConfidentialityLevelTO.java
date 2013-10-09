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
package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ConfidentialityLevelTO extends
		AbstractReferenceTO<ConfidentialityLevel>
		implements TransferObject<ConfidentialityLevel> {

	@NotNull
	@NotEmpty
	private String acronym;
	
	private String dataPermission;

	public ConfidentialityLevelTO() {
		super();
	}

	public ConfidentialityLevelTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ConfidentialityLevelTO(final ConfidentialityLevel model) {
		super();
		from(model);
	}

	public static List<ConfidentialityLevelTO> toTOList(
			final Collection<ConfidentialityLevel> models) {
		final List<ConfidentialityLevelTO> tObjects = Lists.newArrayList();
		for (ConfidentialityLevel model : models) {
			tObjects.add(new ConfidentialityLevelTO(model));
		}
		return tObjects;
	}

	@Override
	public final void from(final ConfidentialityLevel model) {
		super.from(model);
		acronym = model.getAcronym();
		dataPermission = model.getPermission().name();
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}

	public String getDataPermission() {
		return dataPermission;
	}

	public void setDataPermission(String dataPermission) {
		this.dataPermission = dataPermission;
	}

}
