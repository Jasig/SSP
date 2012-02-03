namespace 'ssp.service'

	StudentService:
	
		class StudentService extends ssp.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			getStudent: ( studentId, callbacks ) ->
				$.ajax(
					url: @createURL( "/get" )
					dataType: "json"
					success: ( result ) ->
						student = ssp.model.Student.createFromTransferObject( result )
						callbacks?.result?( student )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)