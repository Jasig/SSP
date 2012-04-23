(($) ->

	namespace 'mygps.model'
	
		SelfHelpGuideResponse:
		
			class SelfHelpGuideResponse
			
				constructor: ( id, summaryText, challengesIdentified, triggeredEarlyAlert ) ->
					@id = ko.observable( id )
					@summaryText = ko.observable( summaryText )
					@challengesIdentified = ko.observable( challengesIdentified )
					@triggeredEarlyAlert = ko.observable( triggeredEarlyAlert )
					
				@createFromTransferObject: ( selfHelpGuideResponseTO ) ->
					if selfHelpGuideResponseTO.challengesIdentified?
						challengesIdentified = for challengeIdentified in selfHelpGuideResponseTO.challengesIdentified 
							mygps.model.Challenge.createFromTransferObject( challengeIdentified )
					return new SelfHelpGuideResponse( selfHelpGuideResponseTO.id, selfHelpGuideResponseTO.summaryText, challengesIdentified, selfHelpGuideResponseTO.triggeredEarlyAlert )

)(jQuery);