namespace 'mygps.service'

	MessageService:
	
		class MessageService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			contactCoach: ( subject, message, callbacks ) ->
				$.ajax(
					url: @createURL( "" )
					type: "POST"
					data: JSON.stringify(
						subject: subject
						message: message
					)
					contentType: "application/json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)