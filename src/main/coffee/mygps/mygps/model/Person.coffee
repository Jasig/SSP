(($) ->

	namespace 'mygps.model'
	
		Person:
		
			class Person
			
				constructor: ( id, firstName, lastName, phoneNumber, emailAddress, photoURL, coach ) ->
					@id = ko.observable( id )
					@firstName = ko.observable( firstName )
					@lastName = ko.observable( lastName )
					@phoneNumber = ko.observable( phoneNumber )
					@photoURL = ko.observable( photoURL )
					@coach = ko.observable( coach )
					
				@createFromTransferObject: ( personTO ) ->
					if personTO.coach?
						coach = mygps.model.Person.createFromTransferObject( personTO.coach )
					return new Person( personTO.id, personTO.firstName, personTO.lastName, personTO.phoneNumber, personTO.emailAddress, personTO.photoURL, coach )

)(jQuery);