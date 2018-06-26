/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.reports;

import org.jasig.ssp.model.ObjectStatus;

import java.util.Date;

public class MapTransferGoalReportTO {

	private String firstName;
	private String lastName;
	private String schoolId;
	private String primaryEmailAddress;
	private Date mapCreatedDate;
	private String mapOwnerFirstName;
	private String mapOwnerLastName;
	private String transferGoal;
	private Boolean partial;
	private String catalogYearCode;
	private String catalogYearName;
	private ObjectStatus objectStatus;

	/**
	 *
	 */
	public MapTransferGoalReportTO() {
		super();
	}

	public MapTransferGoalReportTO(String schoolId, String firstName, String lastName, String primaryEmailAddress,
								   Date mapCreatedDate, String mapOwnerFirstName, String mapOwnerLastName,
								   String transferGoal, Boolean partial, String catalogYearCode, ObjectStatus objectStatus) {
		super();
		this.schoolId = schoolId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryEmailAddress = primaryEmailAddress;
		this.mapCreatedDate = mapCreatedDate;
		this.mapOwnerFirstName = mapOwnerFirstName;
		this.mapOwnerLastName = mapOwnerLastName;
		this.transferGoal = transferGoal;
		this.partial = partial;
		this.catalogYearCode = catalogYearCode;
		this.objectStatus = objectStatus;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public Date getMapCreatedDate() {
		return mapCreatedDate;
	}

	public void setMapCreatedDate(Date mapCreatedDate) {
		this.mapCreatedDate = mapCreatedDate;
	}

	public String getMapOwnerFirstName() {
		return mapOwnerFirstName;
	}

	public void setMapOwnerFirstName(String mapOwnerFirstName) {
		this.mapOwnerFirstName = mapOwnerFirstName;
	}

	public String getMapOwnerLastName() {
		return mapOwnerLastName;
	}

	public void setMapOwnerLastName(String mapOwnerLastName) {
		this.mapOwnerLastName = mapOwnerLastName;
	}

	public String getTransferGoal() {
		return transferGoal;
	}

	public void setTransferGoal(String transferGoal) {
		this.transferGoal = transferGoal;
	}

	public Boolean getPartial() {
		return partial;
	}

	public void setPartial(Boolean partial) {
		this.partial = partial;
	}

	public String getCatalogYearCode() {
		return catalogYearCode;
	}

	public void setCatalogYearCode(String catalogYearCode) {
		this.catalogYearCode = catalogYearCode;
	}

	public String getCatalogYearName() {
		return catalogYearName;
	}

	public void setCatalogYearName(String catalogYearName) {
		this.catalogYearName = catalogYearName;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

}
