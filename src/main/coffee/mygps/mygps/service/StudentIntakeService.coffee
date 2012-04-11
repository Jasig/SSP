namespace 'mygps.service'

	StudentIntakeService:
	
		class StudentIntakeService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			getForm: ( callbacks ) ->
				$.ajax(
					url: @createURL( "/getForm" )
					dataType: "json"
					success: ( result ) ->
						form = mygps.model.Form.createFromTransferObject( result )
						callbacks?.result?( form )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
				
			saveForm: ( form, callbacks ) ->
				$.ajax(
					url: @createURL( "" )
					type: "POST"
					data: JSON.stringify( mygps.model.Form.toTransferObject( form ) )
					contentType: "application/json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
