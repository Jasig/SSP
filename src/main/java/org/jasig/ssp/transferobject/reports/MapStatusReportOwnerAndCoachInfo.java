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

import java.util.UUID;

public class MapStatusReportOwnerAndCoachInfo {
	
	private UUID ownerId;
	
	private String ownerPrimaryEmail;

	private UUID coachId;
	
	private String coachPrimaryEmail;
	
	public MapStatusReportOwnerAndCoachInfo(UUID ownerId, String ownerPrimaryEmail,
											UUID coachId, String coachPrimaryEmail) {
		super();
		this.ownerId = ownerId;
		this.ownerPrimaryEmail = ownerPrimaryEmail;
		this.coachId = coachId;
		this.coachPrimaryEmail = coachPrimaryEmail;
	}
	
	
	public UUID getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getOwnerPrimaryEmail() {
		return ownerPrimaryEmail;
	}
	
	public void setOwnerPrimaryEmail(String ownerPrimaryEmail) {
		this.ownerPrimaryEmail = ownerPrimaryEmail;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}
	
	public String getCoachPrimaryEmail() {
		return coachPrimaryEmail;
	}

	public void setCoachPrimaryEmail(String coachPrimaryEmail) {
		this.coachPrimaryEmail = coachPrimaryEmail;
	}
}
