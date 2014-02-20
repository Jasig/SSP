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

import org.jasig.ssp.model.Auditable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SapStatus extends AbstractReference implements Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4613085897012049359L;
	private String code;

	/**
	 * Constructor
	 */
	public SapStatus() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public SapStatus(final UUID id) {
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

	public SapStatus(final UUID id, final String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public SapStatus(final UUID id, final String name, final String description) {
		super(id, name, description);
	}
	
	/**
	 * @return the race code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param race external ref code
	 * 				the race code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	@Override
	protected int hashPrime() {
		return 111;
	}
	
	@Override
	public int hashCode() { // NOPMD by jon.adams
		return hashPrime() * super.hashCode()
				* hashField("code", code);
	}	
}
