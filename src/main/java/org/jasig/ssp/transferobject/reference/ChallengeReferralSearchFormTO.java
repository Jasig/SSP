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
