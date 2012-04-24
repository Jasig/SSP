namespace 'mygps.viewmodel'

	SelfHelpGuidesViewModel:
		
		class SelfHelpGuidesViewModel extends mygps.viewmodel.AbstractSessionViewModel
			
			constructor: ( session, selfHelpGuideService ) ->
				super( session )
				@selfHelpGuideService = selfHelpGuideService
				@selfHelpGuides = ko.observableArray()
			
			load: ( selfHelpGuideGroupId = null ) ->
				super()
				@loadSelfHelpGuides( selfHelpGuideGroupId )
				return
			
			loadSelfHelpGuides: ( selfHelpGuideGroupId = null ) ->
				if selfHelpGuideGroupId?
					@selfHelpGuideService.getBySelfHelpGuideGroup(
						selfHelpGuideGroupId,
						result: ( result ) =>
							@selfHelpGuides( result )
						fault: ( fault ) =>
							alert( fault.responseText )
					)
				else
					@selfHelpGuideService.getAll(
						result: ( result ) =>
							@selfHelpGuides( result )
						fault: ( fault ) =>
							alert( fault.responseText )
					)
				return
