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
package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SelfHelpGuideQuestionTO extends
		AbstractReferenceTO<SelfHelpGuideQuestion>
		implements TransferObject<SelfHelpGuideQuestion>, Serializable {

	private static final long serialVersionUID = 6074881529172652403L;

	private int questionNumber;

	private boolean critical;

	private boolean mandatory;

	private UUID challengeId;

	private String selfHelpGuideId;

	public SelfHelpGuideQuestionTO() {
		super();
	}

	public SelfHelpGuideQuestionTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SelfHelpGuideQuestionTO(final SelfHelpGuideQuestion model) {
		super();
		from(model);
	}

	public static List<SelfHelpGuideQuestionTO> toTOList(
			final Collection<SelfHelpGuideQuestion> models) {
		final List<SelfHelpGuideQuestionTO> tObjects = Lists.newArrayList();
		for (SelfHelpGuideQuestion model : models) {
			tObjects.add(new SelfHelpGuideQuestionTO(model));
		}
		return tObjects;
	}

	@Override
	public final void from(final SelfHelpGuideQuestion model) {
		super.from(model);
		questionNumber = model.getQuestionNumber();
		critical = model.isCritical();
		mandatory = model.isMandatory();
		//For display purposes we put the challenge name as the question name.
		setName(model.getChallenge().getName());
		if (model.getSelfHelpGuide() != null) {
			selfHelpGuideId = model.getSelfHelpGuide().getId().toString();
		}

		if (model.getChallenge() != null) {
			challengeId = model.getChallenge().getId();
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
		return challengeId;
	}

	public void setChallengeId(final UUID challengeId) {
		this.challengeId = challengeId;
	}

	public String getSelfHelpGuideId() {
		return selfHelpGuideId;
	}

	public void setSelfHelpGuideId(final String selfHelpGuideId) {
		this.selfHelpGuideId = selfHelpGuideId;
	}

}
