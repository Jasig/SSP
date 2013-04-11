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

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.Auditable;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Color extends AbstractReference
	implements Auditable {

	
	private static final long serialVersionUID = 4322529222514468288L;
	
	@Column(nullable = false, length = 10)
	@Size(max = 10)
	private String code;
	
	@Column(nullable = false, length = 25)
	@Size(max = 25)
	private String hexCode;
	
	/**
	 * Constructor
	 */
	public Color() {
		super();
	}
	
	/**
	 * Constructor
	 * @param id 
	 * 			Identifier; required
	 */

	public Color(@NotNull final UUID id) {
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
	public Color(@NotNull final UUID id, @NotNull final String name,
			final String description) {
		super(id, name, description);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHexCode() {
		return hexCode;
	}

	public void setHexCode(String hexCode) {
		this.hexCode = hexCode;
	}

	@Override
	protected int hashPrime() {
		// TODO Auto-generated method stub
		return 337;
	}
	
	@Override
	public int hashCode() {
		int result = hashPrime() * super.hashCode();
		
		result *= hashField("code", code);
		result *= hashField("hexCode", hexCode);
		
		return result;
	}

}
