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

/**
 * An ExternalData requires an identifier for Hibernate, though it is completely
 * ignored by the system.
 * 
 * @author jon.adams
 * 
 */
public interface ExternalData {

	/**
	 * Only used by Hibernate. Generator identifiers of external data are
	 * meaningless to this system.
	 * 
	 * @return the generated identifier
	 */
	@Deprecated
	Serializable getId();

	/**
	 * Only used by Hibernate. Setting this does not matter as changes are never
	 * persisted.
	 * 
	 * @param id
	 */
	@Deprecated
	void setId(Serializable id);
}