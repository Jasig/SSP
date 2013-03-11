package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Comparator;

import org.jasig.ssp.model.reference.ChallengeChallengeReferral;
import org.jasig.ssp.model.reference.ChallengeReferral;

public class ChallengeReferralTOComparator implements Comparator<ChallengeReferralTO>, Serializable{
	

		@Override
		public int compare(final ChallengeReferralTO a, final ChallengeReferralTO b) {
			return a.getName().toUpperCase()
					.compareTo(b.getName().toUpperCase());
		}
}
