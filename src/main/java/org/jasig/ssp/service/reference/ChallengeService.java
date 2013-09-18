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
package org.jasig.ssp.service.reference;

import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeChallengeReferral;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface ChallengeService extends ReferenceService<Challenge> {

	List<Challenge> challengeSearch(String query,boolean selfHelpGuide);

	PagingWrapper<Challenge> getAllForCategory(Category category,
			SortingAndPaging sAndP);

	PagingWrapper<Challenge> getAllForPerson(
			final Person person, final SortingAndPaging sAndP);

	ChallengeChallengeReferral addChallengeReferralToChallenge(
			ChallengeReferral referral, Challenge challenge);

	ChallengeChallengeReferral removeChallengeReferralFromChallenge(
			ChallengeReferral referral, Challenge challenge);

	PagingWrapper<Challenge> getAllForIntake(SortingAndPaging sAndP);


	List<ChallengeTO> search(String query, Person student, boolean selfHelpGuide)
			throws Exception;

}