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
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Elective
		extends AbstractReference
		implements Auditable {
	

	private static final long serialVersionUID = -263405440587922846L;

	@Column(nullable = false, length = 10)
	@Size(max = 10)
	private String code;
	
	@Column(nullable = true)
	private Integer sortOrder;

	// Lazy to try to reduce width of PlanCourse queries which had become
	// too wide for SQLServer
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "color_id", nullable = false )
	private Color color;
	
	/**
	 * Constructor
	 */
	public Elective() {
		super();
	}
	
	/**
	 * Constructor
	 * @param id 
	 * 			Identifier; required
	 */

	public Elective(@NotNull final UUID id) {
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
	public Elective(@NotNull final UUID id, @NotNull final String name,
			final String description) {
		super(id, name, description);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer order) {
		this.sortOrder = order;
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
		
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	protected int hashPrime() {
		return 337;
	}
	
	@Override
	public int hashCode() {
		int result = hashPrime() * super.hashCode();
		
		result *= hashField("code", code);
		result *= hashField("order", sortOrder);
		
		return result;
	}
}
