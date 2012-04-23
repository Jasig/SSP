(($) ->

	namespace 'mygps.service'
	
		SessionService:
		
			class SessionService extends mygps.service.AbstractService
				
				constructor: ( baseURL ) ->
					super( baseURL )
				
				getAuthenticatedPerson: ( callbacks ) ->
					$.ajax(
						url: @createURL( "/getAuthenticatedPerson" )
						dataType: "json"
						success: ( result ) ->
							if result
								person = mygps.model.Person.createFromTransferObject( result )
							else
								person = null
							callbacks?.result?( person )
						error: ( fault ) ->
							callbacks?.fault?( fault )
					)

)(jQuery);