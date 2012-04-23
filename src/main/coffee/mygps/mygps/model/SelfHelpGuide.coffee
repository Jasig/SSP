(($) ->

	namespace 'mygps.model'
	
		SelfHelpGuide:
		
			class SelfHelpGuide
			
				constructor: ( id, name, description ) ->
					@id = ko.observable( id )
					@name = ko.observable( name )
					@description = ko.observable( description )
					
				@createFromTransferObject: ( selfHelpGuideTO ) ->
					return new SelfHelpGuide( selfHelpGuideTO.id, selfHelpGuideTO.name, selfHelpGuideTO.description )

)(jQuery);