(($) ->

	namespace 'mygps.model'
	
		SelfHelpGuideContent:
		
			class SelfHelpGuideContent extends SelfHelpGuide
			
				constructor: ( id, name, description, introductoryText, questions ) ->
					super( id, name, description )
					@introductoryText = ko.observable( introductoryText )
					@questions = ko.observableArray( questions )
					
				@createFromTransferObject: ( selfHelpGuideContentTO ) ->
					if selfHelpGuideContentTO.questions?
						questions = for question in selfHelpGuideContentTO.questions 
							mygps.model.SelfHelpGuideQuestion.createFromTransferObject( question )
					return new SelfHelpGuideContent( selfHelpGuideContentTO.id, selfHelpGuideContentTO.name, selfHelpGuideContentTO.description, selfHelpGuideContentTO.introductoryText, questions )

)(jQuery);