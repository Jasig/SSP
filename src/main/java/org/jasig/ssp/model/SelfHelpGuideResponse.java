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
package org.jasig.ssp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.jasig.ssp.model.reference.SelfHelpGuide;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SelfHelpGuideResponse
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -1245736694871363293L;

	@Column(nullable = false)
	private boolean completed;

	@Column(nullable = false)
	private boolean cancelled;

	@Column(nullable = false)
	private boolean earlyAlertSent;
	
	@Column(length = 32, updatable = false)
	private String sessionId;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "self_help_guide_id", nullable = false)
	private SelfHelpGuide selfHelpGuide;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "selfHelpGuideResponse")
	private Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses = new HashSet<SelfHelpGuideQuestionResponse>(
			0);

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(final boolean completed) {
		this.completed = completed;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isEarlyAlertSent() {
		return earlyAlertSent;
	}

	public void setEarlyAlertSent(final boolean earlyAlertSent) {
		this.earlyAlertSent = earlyAlertSent;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public SelfHelpGuide getSelfHelpGuide() {
		return selfHelpGuide;
	}

	public void setSelfHelpGuide(final SelfHelpGuide selfHelpGuide) {
		this.selfHelpGuide = selfHelpGuide;
	}

	public Set<SelfHelpGuideQuestionResponse> getSelfHelpGuideQuestionResponses() {
		return selfHelpGuideQuestionResponses;
	}

	public void setSelfHelpGuideQuestionResponses(
			final Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses) {
		this.selfHelpGuideQuestionResponses = selfHelpGuideQuestionResponses;
	}

	public static SelfHelpGuideResponse createDefaultForSelfHelpGuideAndPerson(
			final SelfHelpGuide guide, final Person person, String sessionId) {

		final SelfHelpGuideResponse response = new SelfHelpGuideResponse();
		response.setCancelled(false);
		response.setCompleted(false);
		response.setCreatedDate(new Date());
		response.setEarlyAlertSent(false);
		response.setPerson(person);
		response.setSelfHelpGuide(guide);
		response.setSessionId(sessionId);

		return response;
	}

	@Override
	protected int hashPrime() {
		return 31;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:25 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// SelfHelpGuideResponse
		result *= completed ? 3 : 5;
		result *= cancelled ? 7 : 11;
		result *= earlyAlertSent ? 13 : 17;
		result *= hashField("person", person);
		result *= hashField("selfHelpGuide", selfHelpGuide);

		// collections are not included here

		return result;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}