namespace 'mygps.service'

	ChallengeService:
	
		class ChallengeService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			search: ( query, callbacks ) ->
				$.ajax(
					url: @createURL( "/search?query=#{ query }" )
					dataType: "json"
					success: ( result ) ->
						challenges = for challenge in result
							mygps.model.Challenge.createFromTransferObject( challenge )
						callbacks?.result?( challenges )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
