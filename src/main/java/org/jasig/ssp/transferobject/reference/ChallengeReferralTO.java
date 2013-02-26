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

import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChallengeReferralTO extends AbstractReferenceTO<ChallengeReferral>
		implements TransferObject<ChallengeReferral>, Serializable {

	private static final long serialVersionUID = 1193408160654677796L;

	private String publicDescription;

	private Boolean showInSelfHelpGuide;

	private Boolean showInStudentIntake;
	
	private String link;

	public ChallengeReferralTO() {
		super();
	}

	public ChallengeReferralTO(final ChallengeReferral model) {
		super();
		from(model);
		this.link = model.getLink();
	}

	public static List<ChallengeReferralTO> toTOList(
			final Collection<ChallengeReferral> models) {
		final List<ChallengeReferralTO> tObjects = Lists.newArrayList();
		for (final ChallengeReferral model : models) {
			tObjects.add(new ChallengeReferralTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}

	@Override
	public final void from(final ChallengeReferral model) {
		super.from(model);
		publicDescription = model.getPublicDescription();
		showInSelfHelpGuide = model.getShowInSelfHelpGuide();
		showInStudentIntake = model.getShowInStudentIntake();
		link = model.getLink();
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	public void setPublicDescription(final String publicDescription) {
		this.publicDescription = publicDescription;
	}

	/**
	 * @return the showInSelfHelpGuide
	 */
	public Boolean getShowInSelfHelpGuide() {
		return showInSelfHelpGuide;
	}

	/**
	 * @param showInSelfHelpGuide
	 *            the showInSelfHelpGuide to set
	 */
	public void setShowInSelfHelpGuide(final Boolean showInSelfHelpGuide) {
		this.showInSelfHelpGuide = showInSelfHelpGuide;
	}

	/**
	 * @return the showInStudentIntake
	 */
	public Boolean getShowInStudentIntake() {
		return showInStudentIntake;
	}

	/**
	 * @param showInStudentIntake
	 *            the showInStudentIntake to set
	 */
	public void setShowInStudentIntake(final Boolean showInStudentIntake) {
		this.showInStudentIntake = showInStudentIntake;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}