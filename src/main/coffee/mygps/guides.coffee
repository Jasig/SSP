# Reference to jQuery not in global scope
$ = jQuery

# Create context.
context = window.context ||= {}

# Create services.
context.sessionService ||= new mygps.service.SessionService( "../api/1/session" )
context.selfHelpGuideService ||= new mygps.service.SelfHelpGuideService( "../api/1/mygps/selfhelpguide" )

# Create session.
context.session ||= new mygps.session.Session( context.sessionService )

# Parse query string.
selfHelpGuideGroupId = $.parameter( "selfHelpGuideGroupId" )

# Bind to lifecycle events.
$('#guides-page').live( 'pagecreate', ->
    guidesPage = @

    # Create view model.
    viewModel = new mygps.viewmodel.SelfHelpGuidesViewModel( context.session, context.selfHelpGuideService )
    
    # Export the view model to window scope.
    window.viewModel = viewModel
    
    # Load templates and then apply bindings.
    $("body")
        .loadTemplates( 
            bannerTemplate:  "/ssp/MyGPS/templates/banner.html"
            footerTemplate:  "/ssp/MyGPS/templates/footer.html"
            guideTemplate:   "/ssp/MyGPS/templates/guide.html"
        )
        .done( ->
            ko.applyBindings( viewModel, guidesPage )
            return
        )
    
    $('#guides-page').live( 'pagebeforeshow', ->
        # Export the view model to window scope.
        window.viewModel = viewModel
        
        # Load view model data.
        viewModel.load( selfHelpGuideGroupId )
        
        return
    )
    
    return
)