(($) ->

    # Create context.
    context = window.context ||= {}
    
    # Create services.
    context.sessionService ||= new mygps.service.SessionService( "../api/session" )
    context.taskService ||= new mygps.service.TaskService( "../api/mygps/task" )
    context.selfHelpGuideService ||= new mygps.service.SelfHelpGuideService( "../api/mygps/selfhelpguide" )
    context.selfHelpGuideResponseService ||= new mygps.service.SelfHelpGuideResponseService( "../api/mygps/selfhelpguideresponse" )
    context.challengeReferralService ||= new mygps.service.ChallengeReferralService( "../api/mygps/challengereferral" )
    
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
            bannerTemplate:  "/ssp/MyGPS/templates/banner.html"
            footerTemplate:  "/ssp/MyGPS/templates/footer.html"
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
            bannerTemplate:    "/ssp/MyGPS/templates/banner.html"
            footerTemplate:    "/ssp/MyGPS/templates/footer.html"
            questionTemplate:  "/ssp/MyGPS/templates/question.html"
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
            bannerTemplate:     "/ssp/MyGPS/templates/banner.html"
            footerTemplate:     "/ssp/MyGPS/templates/footer.html"
            tasksTemplate:      "/ssp/MyGPS/templates/tasks.html" 
            taskTemplate:       "/ssp/MyGPS/templates/task.html"
            taskDetailTemplate: "/ssp/MyGPS/templates/taskdetail.html"
            challengeTemplate:  "/ssp/MyGPS/templates/challenge.html"
            referralTemplate:   "/ssp/MyGPS/templates/referral.html"
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
            bannerTemplate:  "/ssp/MyGPS/templates/banner.html"
            footerTemplate:  "/ssp/MyGPS/templates/footer.html"
        )
        .done( ->
            ko.applyBindings( viewModel, guideSummaryPage )
            return
        )
    
        return
    )

)(jQuery);