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
package org.jasig.ssp.model.external;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * ExternalData model helper that includes common fields, such as the
 * Hibernate-required unique identifier (even though the identifier is never
 * used by the system otherwise).
 * 
 * @author jon.adams
 * 
 */
@MappedSuperclass
// all ExternalDatas point to an external RDBMS view, so no awareness of cache
// freshness
@Cache(usage = CacheConcurrencyStrategy.NONE)
public abstract class AbstractExternalData implements ExternalData {
	@Id
	@Type(type = "string")
	private Serializable id;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(final Serializable id) {
		this.id = id;
	}
}