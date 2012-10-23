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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SelfHelpGuideQuestionResponse
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = -6385278568384602029L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "self_help_guide_response_id", nullable = false)
	private SelfHelpGuideResponse selfHelpGuideResponse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "self_help_guide_question_id", nullable = false)
	private SelfHelpGuideQuestion selfHelpGuideQuestion;

	@Column(nullable = false)
	private Boolean response;

	@Column(nullable = false)
	private Boolean earlyAlertSent;

	public SelfHelpGuideResponse getSelfHelpGuideResponse() {
		return selfHelpGuideResponse;
	}

	public void setSelfHelpGuideResponse(
			final SelfHelpGuideResponse selfHelpGuideResponse) {
		this.selfHelpGuideResponse = selfHelpGuideResponse;
	}

	public SelfHelpGuideQuestion getSelfHelpGuideQuestion() {
		return selfHelpGuideQuestion;
	}

	public void setSelfHelpGuideQuestion(
			final SelfHelpGuideQuestion selfHelpGuideQuestion) {
		this.selfHelpGuideQuestion = selfHelpGuideQuestion;
	}

	public Boolean getResponse() {
		return response;
	}

	public void setResponse(final Boolean response) {
		this.response = response;
	}

	public Boolean getEarlyAlertSent() {
		return earlyAlertSent;
	}

	public void setEarlyAlertSent(final Boolean earlyAlertSent) {
		this.earlyAlertSent = earlyAlertSent;
	}

	@Override
	protected int hashPrime() {
		return 29;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:15 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// SelfHelpGuideQuestionResponse
		result *= hashField("selfHelpGuideResponse", selfHelpGuideResponse);
		result *= hashField("selfHelpGuideQuestion", selfHelpGuideQuestion);
		result *= response ? 3 : 5;
		result *= earlyAlertSent ? 7 : 11;

		return result;
	}
}