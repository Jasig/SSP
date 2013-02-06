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
package org.jasig.mygps.model.transferobject;

import java.io.Serializable;
import java.util.UUID;

import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.transferobject.TransferObject;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;

/**
 * Transfer object very similar to
 * {@link org.jasig.ssp.transferobject.reference.SelfHelpGuideQuestionTO} except
 * that it maps back to a formal Challenge instance (instead of only the
 * ChallengeId) so the MyGPS TO model does not need to be changed, since it is
 * different from the SSP front-end transfer object.
 * 
 * @author jon.adams
 * 
 */
public class SelfHelpGuideQuestionTO extends
		AbstractReferenceTO<SelfHelpGuideQuestion>
		implements TransferObject<SelfHelpGuideQuestion>, Serializable {

	private static final long serialVersionUID = 6074881529172652403L;

	private int questionNumber;

	private boolean critical;

	private boolean mandatory;

	private ChallengeTO challenge;

	private String selfHelpGuideId;

	public SelfHelpGuideQuestionTO(final SelfHelpGuideQuestion model) {
		super();
		from(model);
	}

	@Override
	public final void from(final SelfHelpGuideQuestion model) {
		super.from(model);
		questionNumber = model.getQuestionNumber();
		critical = model.isCritical();
		mandatory = model.isMandatory();
		//we actually display the challenge name as the questions themselves does not have name
        setName(model.getChallenge().getName());
		if (model.getSelfHelpGuide() != null) {
			selfHelpGuideId = model.getSelfHelpGuide().getId().toString();
		}

		if (model.getChallenge() != null) {
			challenge = new ChallengeTO(model.getChallenge());
		}
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(final int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public boolean isCritical() {
		return critical;
	}

	public void setCritical(final boolean critical) {
		this.critical = critical;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	public UUID getChallengeId() {
		return challenge == null ? null : challenge.getId();
	}

	/**
	 * @return the challenge
	 */
	public ChallengeTO getChallenge() {
		return challenge;
	}

	/**
	 * @param challenge
	 *            the challenge to set
	 */
	public void setChallenge(final ChallengeTO challenge) {
		this.challenge = challenge;
	}

	public String getSelfHelpGuideId() {
		return selfHelpGuideId;
	}

	public void setSelfHelpGuideId(final String selfHelpGuideId) {
		this.selfHelpGuideId = selfHelpGuideId;
	}
}
