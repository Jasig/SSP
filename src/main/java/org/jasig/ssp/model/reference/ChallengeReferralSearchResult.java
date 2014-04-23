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
package org.jasig.ssp.model.reference;

import java.util.UUID;

public class ChallengeReferralSearchResult {

	private String categoryName;
	private UUID categoryId;
	
	private String challengeName;
	private UUID challengeId;
	
	private String challengeReferralName;
	private UUID challengeReferralId;
	private String challengeReferralDescription;
	private String challengeReferralLink;
	
	public ChallengeReferralSearchResult() {
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public UUID getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}

	public String getChallengeName() {
		return challengeName;
	}

	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(UUID challengeId) {
		this.challengeId = challengeId;
	}

	public String getChallengeReferralName() {
		return challengeReferralName;
	}

	public void setChallengeReferralName(String challengeReferralName) {
		this.challengeReferralName = challengeReferralName;
	}

	public UUID getChallengeReferralId() {
		return challengeReferralId;
	}

	public void setChallengeReferralId(UUID challengeReferralId) {
		this.challengeReferralId = challengeReferralId;
	}

	public String getChallengeReferralDescription() {
		return challengeReferralDescription;
	}

	public void setChallengeReferralDescription(String challengeReferralDescription) {
		this.challengeReferralDescription = challengeReferralDescription;
	}

	public String getChallengeReferralLink() {
		return challengeReferralLink;
	}

	public void setChallengeReferralLink(String challengeReferralLink) {
		this.challengeReferralLink = challengeReferralLink;
	}

}
