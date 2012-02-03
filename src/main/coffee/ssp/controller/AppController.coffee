namespace 'ssp.controller'

	AppController:
	
		class AppController
		
			constructor: () ->

				@studentService = new ssp.service.StudentService "/ssp/web/example"
				@student ||= new ssp.model.Student
					
			loadStudent: ( studentId ) ->
				@studentService.getStudent(
					studentId,
					result: ( result ) =>
						@student = result
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return