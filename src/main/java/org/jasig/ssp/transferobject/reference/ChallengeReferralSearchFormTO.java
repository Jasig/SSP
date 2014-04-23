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

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.util.sort.SortingAndPaging;

public class ChallengeReferralSearchFormTO {

	private UUID categoryId;
	
	private UUID challengeId;
	
	private String searchPhrase;
	
	SortingAndPaging sortAndPage;
	


	public ChallengeReferralSearchFormTO(UUID categoryId, UUID challengeId,
			String searchPhrase, SortingAndPaging sortAndPage) {
		super();
		this.categoryId = categoryId;
		this.challengeId = challengeId;
		this.searchPhrase = searchPhrase;
		this.sortAndPage = sortAndPage;
	}

	public UUID getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(UUID challengeId) {
		this.challengeId = challengeId;
	}

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	public SortingAndPaging getSortAndPage() {
		return sortAndPage;
	}

	public void setSortAndPage(SortingAndPaging sortAndPage) {
		this.sortAndPage = sortAndPage;
	}
	
	public Boolean hasSearchPhrase(){
		return StringUtils.isNotBlank(searchPhrase);
	}

}
