# Reference to jQuery not in global scope
$ = jQuery

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
            bannerTemplate:        "/ssp/MyGPS/templates/banner.html"
            footerTemplate:        "/ssp/MyGPS/templates/footer.html"
            sectionTemplate:       "/ssp/MyGPS/templates/form/section.html"
            agreementTemplate:     "/ssp/MyGPS/templates/form/question/agreement.html"
            textareaTemplate:      "/ssp/MyGPS/templates/form/question/textarea.html"
            textinputTemplate:     "/ssp/MyGPS/templates/form/question/textinput.html"
            selectTemplate:        "/ssp/MyGPS/templates/form/question/select.html"
            checklistTemplate:     "/ssp/MyGPS/templates/form/question/checklist.html"
            radiolistTemplate:     "/ssp/MyGPS/templates/form/question/radiolist.html"
            selectOptionTemplate:  "/ssp/MyGPS/templates/form/question/option/select.html"
            checkOptionTemplate:   "/ssp/MyGPS/templates/form/question/option/check.html"
            radioOptionTemplate:   "/ssp/MyGPS/templates/form/question/option/radio.html"
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