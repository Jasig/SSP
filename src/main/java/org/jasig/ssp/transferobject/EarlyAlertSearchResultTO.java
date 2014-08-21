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

import java.util.Date;
import java.util.UUID;

import org.jasig.ssp.model.EarlyAlertSearchResult;

public class EarlyAlertSearchResultTO implements
		TransferObject<EarlyAlertSearchResult> {

	private Date createdDate;

	private String status;

	private Date lastResponseDate;

	private String courseTermName;

	private String courseTitle;

	private String courseName;
	
	private UUID earlyAlertId;
	
	public EarlyAlertSearchResultTO() {
		super();
	}
	
	public EarlyAlertSearchResultTO(EarlyAlertSearchResult model) {
		super();
		from(model);
	}

	@Override
	public void from(EarlyAlertSearchResult model) {
		createdDate = model.getCreatedDate();

		status  = model.getStatus().toString();

		lastResponseDate = model.getLastResponseDate();

		courseTermName = model.getCourseTermName();

		courseTitle = model.getCourseTitle();

		courseName = model.getCourseName();
		
		earlyAlertId = model.getEarlyAlertId();

	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastResponseDate() {
		return lastResponseDate;
	}

	public void setLastResponseDate(Date lastResponseDate) {
		this.lastResponseDate = lastResponseDate;
	}

	public String getCourseTermName() {
		return courseTermName;
	}

	public void setCourseTermName(String courseTermName) {
		this.courseTermName = courseTermName;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}


	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public UUID getEarlyAlertId() {
		return earlyAlertId;
	}

	public void setEarlyAlertId(UUID earlyAlertId) {
		this.earlyAlertId = earlyAlertId;
	}

}
