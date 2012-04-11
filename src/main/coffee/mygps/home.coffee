# Create context.
context = window.context ||= {}

# Create services.
context.sessionService ||= new mygps.service.SessionService( "../api/session" )
context.taskService ||= new mygps.service.TaskService( "../api/mygps/task" )

# Create session.
context.session ||= new mygps.session.Session( context.sessionService )

# Bind to lifecycle events.
$('#home-page').live( 'pagecreate', ( event ) ->
	homePage = @

	# Create view model.
	viewModel = new mygps.viewmodel.HomeViewModel( context.session, context.taskService )	
	
	# Export the view model to window scope.
	window.viewModel = viewModel
	
	# Load templates and then apply bindings.
	$("body")
		.loadTemplates( 
			bannerTemplate:     "templates/banner.html"
			footerTemplate:     "templates/footer.html"
			tasksTemplate:      "templates/tasks.html" 
			taskTemplate:       "templates/task.html"
			taskDetailTemplate: "templates/taskdetail.html"
		)
		.done( ->
			ko.applyBindings( viewModel, homePage )
			return
		)
	
	$('#home-page').live( 'pagebeforeshow', ->
		# Export the view model to window scope.
		window.viewModel = viewModel
		
		# Load view model data.
		viewModel.load()
		
		return
	)
	
	return
)