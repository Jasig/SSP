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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.DisabilityAccommodation;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class DisabilityAccommodationTO extends AbstractReferenceTO<DisabilityAccommodation>
		implements TransferObject<DisabilityAccommodation>, Serializable {

	private static final long serialVersionUID = 1193408160654677796L;

	private String descriptionFieldLabel;

	private Boolean useDescription;
	
	private String descriptionFieldType;

	public DisabilityAccommodationTO() {
		super();
	}

	public DisabilityAccommodationTO(final DisabilityAccommodation model) {
		super();
		from(model);
	}
	
	public DisabilityAccommodationTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}	
	
	@Override
	public final void from(final DisabilityAccommodation model) {
		super.from(model);
		descriptionFieldLabel = model.getDescriptionFieldLabel();
		useDescription = model.getUseDescription();
		descriptionFieldType = model.getDescriptionFieldType();
	}

	public static List<DisabilityAccommodationTO> toTOList(
			final Collection<DisabilityAccommodation> models) {
		final List<DisabilityAccommodationTO> tObjects = Lists.newArrayList();
		for (DisabilityAccommodation model : models) {
			tObjects.add(new DisabilityAccommodationTO(model));
		}
		return tObjects;
	}	
	
	public String getDescriptionFieldLabel() {
		return descriptionFieldLabel;
	}

	public void setDescriptionFieldLabel(final String descriptionFieldLabel) {
		this.descriptionFieldLabel = descriptionFieldLabel;
	}

	/**
	 * @return the useDescription
	 */
	public Boolean getUseDescription() {
		return useDescription;
	}

	/**
	 * @param useDescription
	 *            the useDescription to set
	 */
	public void setUseDescription(final Boolean useDescription) {
		this.useDescription = useDescription;
	}
	
	public String getDescriptionFieldType() {
		return descriptionFieldType;
	}

	public void setDescriptionFieldType(final String descriptionFieldType) {
		this.descriptionFieldType = descriptionFieldType;
	}
}