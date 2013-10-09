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

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.transferobject.TransferObject;

public class ConfidentialityLevelOptionTO 
		implements TransferObject<DataPermissions> {

	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	private int id;	

	public ConfidentialityLevelOptionTO() {
		super();
	}
	
	public ConfidentialityLevelOptionTO(DataPermissions permission) {
		super();
		from(permission);
	}
	@Override
	public void from(DataPermissions model) {
		name = model.name();
		id = model.ordinal();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
