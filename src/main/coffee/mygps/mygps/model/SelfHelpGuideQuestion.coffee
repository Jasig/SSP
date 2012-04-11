namespace 'mygps.model'

	SelfHelpGuideQuestion:
	
		class SelfHelpGuideQuestion
		
			constructor: ( id, headingText, descriptionText, questionText, mandatory ) ->
				@id = ko.observable( id )
				@headingText = ko.observable( headingText )
				@descriptionText = ko.observable( descriptionText )
				@questionText = ko.observable( questionText )
				@mandatory = ko.observable( mandatory )
				
				@response = ko.observable( null )
				
			@createFromTransferObject: ( selfHelpGuideQuestionTO ) ->
				return new SelfHelpGuideQuestion( selfHelpGuideQuestionTO.id, selfHelpGuideQuestionTO.headingText, selfHelpGuideQuestionTO.descriptionText, selfHelpGuideQuestionTO.questionText, selfHelpGuideQuestionTO.mandatory )