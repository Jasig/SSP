# Create context.
context = window.context ||= {}

# Create services.
context.sessionService ||= new mygps.service.SessionService( "../api/1/session" )
context.taskService ||= new mygps.service.TaskService( "../api/1/mygps/task" )
context.selfHelpGuideService ||= new mygps.service.SelfHelpGuideService( "../api/1/mygps/selfhelpguide" )
context.selfHelpGuideResponseService ||= new mygps.service.SelfHelpGuideResponseService( "../api/1/mygps/selfhelpguideresponse" )
context.challengeReferralService ||= new mygps.service.ChallengeReferralService( "../api/1/mygps/challengereferral" )

# Create session.
context.session ||= new mygps.session.Session( context.sessionService )

# Parse query string.
selfHelpGuideId = $.parameter( "selfHelpGuideId" )

# Create view model (used across all pages) and load view model data.
viewModel = new mygps.viewmodel.SelfHelpGuideViewModel( context.session, context.taskService, context.selfHelpGuideService, context.selfHelpGuideResponseService, context.challengeReferralService )
viewModel.load( selfHelpGuideId )

# Export the view model to window scope.
window.viewModel = viewModel

# Bind to lifecycle events.
$('#guide-introduction-page').live( 'pagecreate', ->
	guideIntroductionPage = @

	$("body")
	.loadTemplates( 
		bannerTemplate:  "templates/banner.html"
		footerTemplate:  "templates/footer.html"
	)
	.done( ->
		ko.applyBindings( viewModel, guideIntroductionPage )
		return
	)
	
	return
)

$('#guide-question-page').live( 'pagecreate', ->
	guideQuestionPage = @
	
	# Load templates and then apply bindings.
	
	$("body")
	.loadTemplates( 
		bannerTemplate:    "templates/banner.html"
		footerTemplate:    "templates/footer.html"
		questionTemplate:  "templates/question.html"
	)
	.done( ->
		ko.applyBindings( viewModel, guideQuestionPage )
		return
	)
	
	return
)

$('#guide-referrals-page').live( 'pagecreate', ->
	guideReferralsPage = @
	
	# Load templates and then apply bindings.
	
	$("body")
	.loadTemplates( 
		bannerTemplate:     "templates/banner.html"
		footerTemplate:     "templates/footer.html"
		tasksTemplate:      "templates/tasks.html" 
		taskTemplate:       "templates/task.html"
		taskDetailTemplate: "templates/taskdetail.html"
		challengeTemplate:  "templates/challenge.html"
		referralTemplate:   "templates/referral.html"
	)
	.done( ->
		ko.applyBindings( viewModel, guideReferralsPage )
		return
	)
	
	return
)

$('#guide-summary-page').live( 'pagecreate', ->
	guideSummaryPage = @

	$("body")
	.loadTemplates( 
		bannerTemplate:  "templates/banner.html"
		footerTemplate:  "templates/footer.html"
	)
	.done( ->
		ko.applyBindings( viewModel, guideSummaryPage )
		return
	)

	return
)