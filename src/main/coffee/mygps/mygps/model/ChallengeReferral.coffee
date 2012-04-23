(($) ->

	namespace 'mygps.model'
	
		ChallengeReferral:
		
			class ChallengeReferral
			
				constructor: ( id, name, description, details ) ->
					@id = ko.observable( id )
					@name = ko.observable( name )
					@description = ko.observable( description )
					@details = ko.observable( details )
					
				@createFromTransferObject: ( challengeReferralTO ) ->
					return new ChallengeReferral( challengeReferralTO.id, challengeReferralTO.name, challengeReferralTO.description, challengeReferralTO.details )

)(jQuery);