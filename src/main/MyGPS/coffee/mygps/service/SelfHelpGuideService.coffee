namespace 'mygps.service'

	SelfHelpGuideService:
	
		class SelfHelpGuideService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			getAll: ( callbacks ) ->
				$.ajax(
					url: @createURL( "/getAll" )
					dataType: "json"
					success: ( result ) ->
						selfHelpGuides = for selfHelpGuide in result
							mygps.model.SelfHelpGuide.createFromTransferObject( selfHelpGuide )
						callbacks?.result?( selfHelpGuides )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			getBySelfHelpGuideGroup: ( selfHelpGuideGroupId, callbacks ) ->
				$.ajax(
					url: @createURL( "/getBySelfHelpGuideGroup?selfHelpGuideGroupId=#{ selfHelpGuideGroupId }" )
					dataType: "json"
					success: ( result ) ->
						selfHelpGuides = for selfHelpGuide in result
							mygps.model.SelfHelpGuide.createFromTransferObject( selfHelpGuide )
						callbacks?.result?( selfHelpGuides )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
							
			getContentById: ( selfHelpGuideId, callbacks ) ->
				$.ajax(
					url: @createURL( "/getContentById?selfHelpGuideId=#{ selfHelpGuideId }" )
					dataType: "json"
					success: ( result ) ->
						selfHelpGuideContent = mygps.model.SelfHelpGuideContent.createFromTransferObject( result )
						callbacks?.result?( selfHelpGuideContent )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
