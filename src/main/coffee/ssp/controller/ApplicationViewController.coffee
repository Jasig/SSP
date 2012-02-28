namespace 'ssp.controller'

	ApplicationViewController:
	
		class ApplicationViewController
		
			constructor: () ->
				@currentPage = "partials/student.html"
				@currentTool = ""
				@currentSubNav = "partials/tools-nav.html"
				@currentProfile = "partials/profile.html"
				
			displayTool: ( toolName ) ->
				@currentTool = "partials/tools/" + toolName + ".html"