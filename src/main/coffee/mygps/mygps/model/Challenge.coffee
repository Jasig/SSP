namespace 'mygps.model'

	Challenge:
	
		class Challenge
		
			constructor: ( id, name, description, referralCount ) ->
				@id = ko.observable( id )
				@name = ko.observable( name )
				@description = ko.observable( description )
				@referralCount = ko.observable( referralCount )
				
				@challengeReferrals = ko.observableArray()
				
			@createFromTransferObject: ( challengeTO ) ->
				return new Challenge( challengeTO.id, challengeTO.name, challengeTO.description, challengeTO.referralCount )