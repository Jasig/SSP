(($) ->

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

)(jQuery);