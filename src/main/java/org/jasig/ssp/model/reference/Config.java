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
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;

/**
 * Config reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Config
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -8572858642333315262L;

	@Column(nullable = true)
	private String value;

	@NotNull
	@Column(nullable = false)
	private String defaultValue;

	/**
	 * A regular expression for validation the value
	 */
	@Column(nullable = true)
	private String valueValidation;

	@Column(nullable = false)
	@NotNull
	private short sortOrder = 0; // NOPMD by jon.adams on 5/4/12 1:41 PM

	/**
	 * Constructor
	 */
	public Config() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Config(@NotNull final UUID id) {
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
	 * @param sortOrder
	 *            Default sort order when displaying objects to the user
	 */
	public Config(@NotNull final UUID id, @NotNull final String name,
			final String description, final short sortOrder) { // NOPMD by jon
		super(id, name, description);
		this.sortOrder = sortOrder;
	}

	/**
	 * Gets the default sort order when displaying an item list to the user
	 * 
	 * @return the sortOrder
	 */
	public short getSortOrder() { // NOPMD by jon.adams on 5/4/12 1:41 PM
		return sortOrder;
	}

	/**
	 * Sets the default sort order when displaying an item list to the user
	 * 
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(final short sortOrder) { // NOPMD by jon.adams
		this.sortOrder = sortOrder;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getValueValidation() {
		return valueValidation;
	}

	public void setValueValidation(final String valueValidation) {
		this.valueValidation = valueValidation;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(@NotNull final String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Unique (amongst all Models in the system) prime for use by
	 * {@link #hashCode()}
	 */
	@Override
	protected int hashPrime() {
		return 263;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		return hashPrime()
				* super.hashCode()
				* hashField("sortOrder", sortOrder)
				* hashField("value", value)
				* hashField("defaultValue", defaultValue)
				* hashField("valueValidation", valueValidation);
	}
}