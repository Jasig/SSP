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
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * ChallengeReferral service
 */
public interface ChallengeReferralService extends
		ReferenceService<ChallengeReferral> {

	List<ChallengeReferral> getChallengeReferralsByChallengeId(
			Challenge challenge);

	PagingWrapper<ChallengeReferral> getAllForChallenge(Challenge challenge,
			SortingAndPaging sAndP);

	List<ChallengeReferral> challengeReferralSearch(Challenge challenge,boolean selfHelpGuide);

	List<ChallengeReferral> getChallengeReferralCountByChallengeAndQuery(Challenge challenge,
			String query, SortingAndPaging sAndP);

	long countByChallengeIdNotOnActiveTaskList(Challenge challenge,
			Person student, String sessionId, boolean selfHelpGuide);

	List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			Challenge challenge, Person student, String sessionId, boolean selfHelpGuide);
}