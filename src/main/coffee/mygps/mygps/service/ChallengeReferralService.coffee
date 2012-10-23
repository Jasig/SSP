#
# Licensed to Jasig under one or more contributor license
# agreements. See the NOTICE file distributed with this work
# for additional information regarding copyright ownership.
# Jasig licenses this file to you under the Apache License,
# Version 2.0 (the "License"); you may not use this file
# except in compliance with the License. You may obtain a
# copy of the License at:
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on
# an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied. See the License for the
# specific language governing permissions and limitations
# under the License.
#

namespace 'mygps.service'

	ChallengeReferralService:
	
		class ChallengeReferralService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			getByChallengeId: ( challengeId, callbacks ) ->
				$.ajax(
					url: @createURL( "/getByChallengeId?challengeId=#{ challengeId }" )
					dataType: "json"
					success: ( result ) ->
						challengeReferrals = for challengeReferral in result
							mygps.model.ChallengeReferral.createFromTransferObject( challengeReferral )
						callbacks?.result?( challengeReferrals )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			search: ( challengeId, query, callbacks ) ->
				$.ajax(
					url: @createURL( "/search?challengeId=#{ challengeId }&query=#{ query }" )
					dataType: "json"
					success: ( result ) ->
						challengeReferrals = for challengeReferral in result
							mygps.model.ChallengeReferral.createFromTransferObject( challengeReferral )
						callbacks?.result?( challengeReferrals )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
