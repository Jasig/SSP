namespace 'mygps.service'

	SelfHelpGuideResponseService:
	
		class SelfHelpGuideResponseService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			cancel: ( selfHelpGuideResponseId, callbacks ) ->
				$.ajax(
					url: @createURL( "/cancel?selfHelpGuideResponseId=#{ selfHelpGuideResponseId }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
				
			complete: ( selfHelpGuideResponseId, callbacks ) ->
				$.ajax(
					url: @createURL( "/complete?selfHelpGuideResponseId=#{ selfHelpGuideResponseId }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			getById: ( selfHelpGuideResponseId, callbacks ) ->
				$.ajax(
					url: @createURL( "/getById?selfHelpGuideResponseId=#{ selfHelpGuideResponseId }" )
					dataType: "json"
					success: ( result ) ->
						selfHelpGuideResponse = mygps.model.SelfHelpGuideResponse.createFromTransferObject( result )
						callbacks?.result?( selfHelpGuideResponse )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			initiate: ( selfHelpGuideId, callbacks ) ->
				$.ajax(
					url: @createURL( "/initiate?selfHelpGuideId=#{ selfHelpGuideId }" )
					dataType: "text"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			answer: ( selfHelpGuideResponseId, selfHelpGuideQuestionId, response, callbacks ) ->
				$.ajax(
					url: @createURL( "/answer?selfHelpGuideResponseId=#{ selfHelpGuideResponseId }&selfHelpGuideQuestionId=#{ selfHelpGuideQuestionId }&response=#{ response }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
