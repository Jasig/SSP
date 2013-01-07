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
package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.Auditable;

/**
 * ChallengeReferral reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DisabilityAccommodation
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 5543482960025703474L;

	/**
	 * Label to use in the UI for the personDisabilityAccommodation description field
	 * when useDescription is set to true.
	 * 
	 * Optional, null allowed, max length 80 characters.
	 */
	@Column(nullable = true, length = 80)
	@Size(max = 80)
	private String descriptionFieldLabel;

	@Column(nullable = false)
	private Boolean useDescription;

	/**
	 * Field Type for use in the UI when useDescription is set to true.
	 * Can be apply a TextArea or TextField. Use "long" or "short" value
	 * for each respectively.  
	 * 
	 * Optional, null allowed, max length 80 characters.
	 */
	@Column(nullable = true, length = 80)
	@Size(max = 80)
	private String descriptionFieldType;	
	
	/**
	 * Constructor
	 */
	public DisabilityAccommodation() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public DisabilityAccommodation(final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 */

	public DisabilityAccommodation(final UUID id, final String name) {
		super(id, name);
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
	 *            the useDescription to set, can be null
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
	
	@Override
	protected int hashPrime() {
		return 367;
	}

	@Override
	public int hashCode() { // NOPMD
		int result = hashPrime() * super.hashCode();

		result *= hashField("descriptionFieldLabel", descriptionFieldLabel);
		result *= useDescription != null && useDescription ? 5 : 11;
		result *= hashField("descriptionFieldType", descriptionFieldType);

		// collections are not included here

		return result;
	}
}