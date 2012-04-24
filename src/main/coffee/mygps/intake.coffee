# Create context.
context = window.context ||= {}

# Create services.
context.sessionService ||= new mygps.service.SessionService( "../api/1/session" )
context.studentIntakeService ||= new mygps.service.StudentIntakeService( "../api/1/mygps/intake" )

# Create session.
context.session ||= new mygps.session.Session( context.sessionService )

# Bind to lifecycle events.
$('#intake-page').live( 'pagecreate', ( event ) ->
	intakePage = @

	# Create view model.
	viewModel = new mygps.viewmodel.StudentIntakeViewModel( context.session, context.studentIntakeService )	
	
	# Export the view model to window scope.
	window.viewModel = viewModel
	
	# Load templates and then apply bindings.
	$("body")
		.loadTemplates( 
			bannerTemplate:        "templates/banner.html"
			footerTemplate:        "templates/footer.html"
			sectionTemplate:       "templates/form/section.html"
			agreementTemplate:     "templates/form/question/agreement.html"
			textareaTemplate:      "templates/form/question/textarea.html"
			textinputTemplate:     "templates/form/question/textinput.html"
			selectTemplate:        "templates/form/question/select.html"
			checklistTemplate:     "templates/form/question/checklist.html"
			radiolistTemplate:     "templates/form/question/radiolist.html"
			selectOptionTemplate:  "templates/form/question/option/select.html"
			checkOptionTemplate:   "templates/form/question/option/check.html"
			radioOptionTemplate:   "templates/form/question/option/radio.html"
		)
		.done( ->
			ko.applyBindings( viewModel, intakePage )
			return
		)
	
	$('#intake-page').live( 'pagebeforeshow', ->
		# Export the view model to window scope.		
		window.viewModel = viewModel
		
		# Load view model data.
		viewModel.load()
		
		return
	)
	
	return
)