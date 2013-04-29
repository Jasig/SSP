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

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;

/**
 * Tag reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Tag
		extends AbstractReference
		implements Auditable {

	//private static final long serialVersionUID  = 7607015313995397895L;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1951851647836192289L;
	private String code;

	/**
	 * Constructor
	 */
	public Tag() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Tag(@NotNull final UUID id) {
		super(id);
	}

	

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 * @param description
	 *            Description; max 64000 characters
	 */
	public Tag(@NotNull final UUID id, @NotNull final String name,
			final String description) {
		super(id, name, description);
	}
	
	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public Boolean getActive() {
			return this.getObjectStatus().equals(ObjectStatus.ACTIVE);
	}
	
	public void setActive(Boolean active) {
		if(active) {
		this.setObjectStatus(ObjectStatus.ACTIVE);
		} else {
		this.setObjectStatus(ObjectStatus.INACTIVE);
		}	
	}

	@Override
	protected int hashPrime() {
		return 373;
	}
	
	@Override
	public int hashCode() { 
		int result = hashPrime() * super.hashCode();

		result *= hashField("code", code);
		
		return result;
	}
	

	// default hashCode okay if no extra fields are added
}
