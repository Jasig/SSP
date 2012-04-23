(($) ->

	# Create context.
	context = window.context ||= {}
	
	# Create services.
	context.sessionService ||= new mygps.service.SessionService( "../api/session" )
	context.messageService ||= new mygps.service.MessageService( "../api/mygps/message" )
	
	# Create session.
	context.session ||= new mygps.session.Session( context.sessionService )
	
	# Bind to lifecycle events.
	$('#contact-page').live( 'pagecreate', ->
		contactPage = @
	
		# Create view model.
		viewModel = new mygps.viewmodel.ContactViewModel( context.session, context.messageService )
		
		# Export the view model to window scope.
		window.viewModel = viewModel
		
		# Load templates and then apply bindings.
		$("body")
			.loadTemplates( 
				bannerTemplate:  "/ssp/MyGPS/templates/banner.html"
				footerTemplate:  "/ssp/MyGPS/templates/footer.html"
			)
			.done( ->
				ko.applyBindings( viewModel, contactPage )
				return
			)
		
		# Load view model data.
		$('#contact-page').live( 'pagebeforeshow', ->
			# Export the view model to window scope.
			window.viewModel = viewModel
			
			# Load view model data.
			viewModel.load()
			
			return
		)
		
		return
	)

)(jQuery);