package org.studentsuccessplan.mygps.model.transferobject;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;

import com.google.common.collect.Lists;

public class ChallengeReferralTO {

	public static final int SHORTENED_TEXT_LENGTH = 140;

	private UUID id;
	private String name;
	private String description;
	private String details;

	public ChallengeReferralTO() {
	}

	public ChallengeReferralTO(ChallengeReferral challengeReferral) {
		if ((challengeReferral.getPublicDescription() != null)
				&& (challengeReferral.getPublicDescription().length() > SHORTENED_TEXT_LENGTH)) {
			setDescription(challengeReferral.getPublicDescription().substring(
					0, SHORTENED_TEXT_LENGTH)
					+ "...");
		} else {
			setDescription(challengeReferral.getPublicDescription());
		}

		setDetails(challengeReferral.getPublicDescription());
		setId(challengeReferral.getId());
		setName(challengeReferral.getName());
	}

	public static List<ChallengeReferralTO> listToTOList(
			List<ChallengeReferral> models) {
		List<ChallengeReferralTO> tos = Lists.newArrayList();
		for (ChallengeReferral model : models) {
			tos.add(new ChallengeReferralTO(model));
		}
		return tos;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
