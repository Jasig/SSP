(($) ->

    # Create context.
    context = window.context ||= {}
    
    # Bind to lifecycle events.
    $('#login-page').live( 'pagecreate', ( event ) ->
        loginPage = @
        
        # Create view model.
        viewModel = new mygps.viewmodel.LoginViewModel()    
        
        # Export the view model to window scope.
        window.viewModel = viewModel
        
        # Load templates and then apply bindings.
        $("body")
            .loadTemplates( 
                bannerTemplate:  "/ssp/MyGPS/templates/banner.html"
                footerTemplate:  "/ssp/MyGPS/templates/footer.html"
            )
            .done( ->
                ko.applyBindings( viewModel, loginPage )
                return
            )
        
        $('#login-page').live( 'pagebeforeshow', ->
            # Export the view model to window scope.        
            window.viewModel = viewModel
            
            # Load view model data.
            viewModel.load()
            
            return
        )
    
        $('#login-page').live( 'pageshow', ->
            $('#login-page').ready( ->
                # Focus on the username input.
                $('#j_username').focus()
            )
            
            return
        )
        
        return
    )

)(jQuery);