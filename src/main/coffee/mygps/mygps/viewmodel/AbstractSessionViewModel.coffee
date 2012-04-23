(($) ->

	namespace 'mygps.viewmodel'
	
		AbstractSessionViewModel:
			
			class AbstractSessionViewModel
				
				constructor: ( session ) ->
					@session = session
					
					@authenticated = ko.dependentObservable( @evaluateAuthenticated, this )
					@authenticatedPersonName = ko.dependentObservable( @authenticatedPersonName, this )
					
				load: () ->
					return
					
				evaluateAuthenticated: () ->
					return @session?.authenticatedPerson()?;
				
				authenticatedPersonName: () ->
					person = @session?.authenticatedPerson()
					if person?
						return "#{ person.firstName() } #{ person.lastName() }"
					return null

)(jQuery);