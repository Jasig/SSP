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
package org.jasig.ssp.transferobject.reports;

import java.util.List;
import java.util.UUID;

public class CaseLoadSearchTO {
	
	List<UUID> studentTypeIds;
	List<UUID> serviceReasonIds;
	List<UUID> specialServiceGroupIds;
	String homeDepartment;
	
	
	public CaseLoadSearchTO()
	{
		super();
	}
	
	/**
	 * @param studentTypeIds
	 * @param serviceReasonIds
	 * @param homeDepartment
	 */
	public CaseLoadSearchTO(List<UUID> studentTypeIds,
			List<UUID> serviceReasonIds, List<UUID> specialServiceGroupIds, String homeDepartment) {
		super();
		this.studentTypeIds = studentTypeIds;
		this.serviceReasonIds = serviceReasonIds;
		this.homeDepartment = homeDepartment;
		this.specialServiceGroupIds = specialServiceGroupIds;
	}
	
	/**
	 * @return the studentTypeIds
	 */
	public List<UUID> getStudentTypeIds() {
		return studentTypeIds;
	}
	/**
	 * @param studentTypeIds the studentTypeIds to set
	 */
	public void setStudentTypeIds(List<UUID> studentTypeIds) {
		this.studentTypeIds = studentTypeIds;
	}
	/**
	 * @return the serviceReasonIds
	 */
	public List<UUID> getServiceReasonIds() {
		return serviceReasonIds;
	}
	/**
	 * @param serviceReasonIds the serviceReasonIds to set
	 */
	public void setServiceReasonIds(List<UUID> serviceReasonIds) {
		this.serviceReasonIds = serviceReasonIds;
	}
	/**
	 * @return the specialServiceGroupIds
	 */
	public List<UUID> getSpecialServiceGroupIds() {
		return specialServiceGroupIds;
	}

	/**
	 * @param specialServiceGroupIds the specialServiceGroupIds to set
	 */
	public void setSpecialServiceGroupIds(List<UUID> specialServiceGroupIds) {
		this.specialServiceGroupIds = specialServiceGroupIds;
	}

	/**
	 * @return the homeDepartment
	 */
	public String getHomeDepartment() {
		return homeDepartment;
	}
	/**
	 * @param homeDepartment the homeDepartment to set
	 */
	public void setHomeDepartment(String homeDepartment) {
		this.homeDepartment = homeDepartment;
	}

}
