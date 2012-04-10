namespace 'mygps.service'

	TaskService:
	
		class TaskService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			createCustom: ( name, description, callbacks ) ->
				$.ajax(
					url: @createURL( "/createCustom?name=#{ encodeURIComponent( name ) }&description=#{ encodeURIComponent( description ) }" )
					dataType: "json"
					success: ( result ) ->
						task = mygps.model.Task.createFromTransferObject( result )
						callbacks?.result?( task )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			createForChallengeReferral: ( challengeId, challengeReferralId, callbacks ) ->
				$.ajax(
					url: @createURL( "/createForChallengeReferral?challengeId=#{ challengeId }&challengeReferralId=#{ challengeReferralId }" )
					dataType: "json"
					success: ( result ) ->
						task = mygps.model.Task.createFromTransferObject( result )
						callbacks?.result?( task )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			delete: ( taskId, callbacks ) ->
				$.ajax(
					url: @createURL( "/delete?taskId=#{ taskId }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			email: ( emailAddress, callbacks ) ->
				$.ajax(
					url: @createURL( "/email?emailAddress=#{ emailAddress }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			getAll: ( callbacks ) ->
				$.ajax(
					url: @createURL( "/getAll" )
					dataType: "json"
					success: ( result ) ->
						tasks = for task in result
							mygps.model.Task.createFromTransferObject( task )
						callbacks?.result?( tasks )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			mark: ( taskId, complete, callbacks ) ->
				$.ajax(
					url: @createURL( "/mark?taskId=#{ taskId }&complete=#{ complete }" )
					dataType: "json"
					success: ( result ) ->
						task = mygps.model.Task.createFromTransferObject( result )
						callbacks?.result?( task )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			print: ( callbacks ) ->
				window.open( @createURL( "/print" ) )
				result( true )
				###
				$.ajax(
					url: @createURL( "/print" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
				###
