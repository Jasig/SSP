namespace 'mygps.session'

	Session:
		
		class Session
			
			constructor: ( sessionService ) ->
				@sessionService = sessionService
				@authenticatedPerson = ko.observable( null )
				
				@initialized = ko.observable( false )
				@sessionService.getAuthenticatedPerson(
					result: ( result ) =>
						@authenticatedPerson( result )
						@initialized( true )
					fault: ( fault ) =>
						alert( fault.responseText )
						@initialized( true )
				)

