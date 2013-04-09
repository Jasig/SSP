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

import org.jasig.ssp.model.AbstractPlan;

/**
 */
public abstract class AbstractPlanTO<T extends AbstractPlan> extends AbstractAuditableTO<T>
		implements TransferObject<T> {

	private String name;
	
	private String ownerId;
	/**
	 * Empty constructor.
	 */
	public AbstractPlanTO() {
		super();
	}

	
	@Override
	public void from(T model) {
		super.from(model);
		this.setName(model.getName());
		this.setOwnerId(model.getOwner().getId().toString());
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getOwnerId() {
		return ownerId;
	}


	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}