# Create context.
context = window.context ||= {}

# Bind to lifecycle events.
$('#home-page').live( 'pagecreate', ( event ) ->
	homePage = @

	# Create view model.
	viewModel = new ssp.controller.ApplicationViewController()	
	
	# Export the view model to window scope.
	window.viewModel = viewModel
	
	# Load templates and then apply bindings.
	$("body")
		.loadTemplates( 
			headerTemplate:		"partials/header.html"
			navTemplate:		"partials/top-nav.html"
			profileTemplate:	"partials/profile.html"
			toolsNavTemplate:	"partials/tools-nav.html"
			intakeTemplate:		"partials/tools/student-intake.html"
			journalTemplate:	"partials/tools/journal.html"
			studentTemplate:	"partials/student.html"
			footerTemplate:		"partials/footer.html"
		)
		.done( ->
			ko.applyBindings( viewModel, homePage )
			return
		)
	
	$('#home-page').live( 'pagebeforeshow', ->
		# Export the view model to window scope.
		window.viewModel = viewModel
		
		return
	)
	
	return
)