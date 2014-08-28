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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.EarlyAlertRouting;

import com.google.common.collect.Lists;

import org.jasig.ssp.transferobject.reference.ConfidentialityLevelTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertReasonTO;

/**
 * EarlyAlertRouting transfer object
 * 
 * @author jon.adams
 */
public class EarlyAlertRoutingTO
		extends AbstractAuditableTO<EarlyAlertRouting>
		implements TransferObject<EarlyAlertRouting>, Serializable {

	private static final long serialVersionUID = 5436157638643860410L;

	private String groupName;

	private String groupEmail;

	private UUID campusId;

	private UUID earlyAlertReasonId;

	private PersonLiteTO person;
	
	private EarlyAlertReasonTO earlyAlertReason;

	/**
	 * Empty constructor
	 */
	public EarlyAlertRoutingTO() {
		super();
	}

	/**
	 * Create a transfer object equivalent to the specified model
	 * 
	 * @param model
	 *            Model to copy
	 */
	public EarlyAlertRoutingTO(final EarlyAlertRouting model) {
		super();
		from(model);
	}

	@Override
	public final void from(final EarlyAlertRouting model) {
		super.from(model);

		groupName = model.getGroupName();
		groupEmail = model.getGroupEmail();
		campusId = model.getCampus() == null ? null : model.getCampus().getId();
		earlyAlertReasonId = model.getEarlyAlertReason() == null ? null : model
				.getEarlyAlertReason().getId();
		person = model.getPerson() == null ? null : new PersonLiteTO(
				model.getPerson());
		earlyAlertReason = model.getEarlyAlertReason() == null ? null : new EarlyAlertReasonTO(
				model.getEarlyAlertReason());
		
	}

	/**
	 * Convert a collection of models to a list of equivalent transfer objects.
	 * 
	 * @param models
	 *            Collection of models to copy
	 * @return List of equivalent transfer objects
	 */
	public static List<EarlyAlertRoutingTO> toTOList(
			final Collection<EarlyAlertRouting> models) {
		final List<EarlyAlertRoutingTO> tObjects = Lists.newArrayList();
		for (final EarlyAlertRouting model : models) {
			tObjects.add(new EarlyAlertRoutingTO(model)); // NOPMD
		}

		return tObjects;
	}

	/**
	 * Gets the group name
	 * 
	 * @return the group name
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Sets the group name.
	 * 
	 * @param groupName
	 *            the group name; optional; max 255 characters
	 */
	public void setGroupName(final String groupName) {
		this.groupName = groupName;
	}

	/**
	 * Gets the group email
	 * 
	 * @return the group email
	 */
	public String getGroupEmail() {
		return groupEmail;
	}

	/**
	 * Sets the group email
	 * 
	 * @param groupEmail
	 *            the group email; max 255 characters
	 */
	public void setGroupEmail(final String groupEmail) {
		this.groupEmail = groupEmail;
	}

	/**
	 * Gets the associated Campus identifier
	 * 
	 * @return the CampusId
	 */
	public UUID getCampusId() {
		return campusId;
	}

	/**
	 * Sets the associated Campus by identifier
	 * 
	 * @param campusId
	 *            campus id
	 */
	public void setCampusId(@NotNull final UUID campusId) {
		this.campusId = campusId;
	}

	/**
	 * Gets the associated EarlyAlertReason identifier
	 * 
	 * @return the EarlyAlertReasonId
	 */
	public UUID getEarlyAlertReasonId() {
		return earlyAlertReasonId;
	}

	/**
	 * Sets the associated EarlyAlertReason by identifier
	 * 
	 * @param earlyAlertReasonId
	 *            earlyAlertReason id
	 */
	public void setEarlyAlertReasonId(@NotNull final UUID earlyAlertReasonId) {
		this.earlyAlertReasonId = earlyAlertReasonId;
	}

	/**
	 * Gets the associated Person
	 * 
	 * @return the person
	 */
	public PersonLiteTO getPerson() {
		return person;
	}

	/**
	 * Sets the associated Person
	 * 
	 * @param person
	 *            the person
	 */
	public void setPerson(final PersonLiteTO person) {
		this.person = person;
	}
	
	public EarlyAlertReasonTO getEarlyAlertReason() {
		return earlyAlertReason;
	}
	
	public void setEarlyAlertReason(
			final EarlyAlertReasonTO earlyAlertReason) {
		this.earlyAlertReason = earlyAlertReason;
	}
}