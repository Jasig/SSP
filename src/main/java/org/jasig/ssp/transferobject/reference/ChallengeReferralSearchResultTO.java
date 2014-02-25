package org.jasig.ssp.transferobject.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.ChallengeReferralSearchResult;
import org.jasig.ssp.transferobject.TransferObject;

public class ChallengeReferralSearchResultTO implements TransferObject<ChallengeReferralSearchResult> {

	private String categoryName;
	private UUID categoryId;

	private String challengeName;
	private UUID challengeId;

	private String challengeReferralName;
	private UUID challengeReferralId;
	private String challengReferralDescription;

	public ChallengeReferralSearchResultTO() {
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

	public String getChallengReferralDescription() {
		return challengReferralDescription;
	}

	public void setChallengReferralDescription(String challengReferralDescription) {
		this.challengReferralDescription = challengReferralDescription;
	}

	@Override
	public void from(ChallengeReferralSearchResult model) {
		this.challengeReferralId = model.getChallengeReferralId();
		this.categoryId = model.getCategoryId();
		this.challengeId = model.getChallengeId();
		
		this.challengeReferralName = model.getChallengeReferralName();
		this.categoryName = model.getCategoryName();
		this.challengeName = model.getChallengeName();
		
	}

}
