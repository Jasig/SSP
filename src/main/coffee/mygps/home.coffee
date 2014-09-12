#
# Licensed to Jasig under one or more contributor license
# agreements. See the NOTICE file distributed with this work
# for additional information regarding copyright ownership.
# Jasig licenses this file to you under the Apache License,
# Version 2.0 (the "License"); you may not use this file
# except in compliance with the License. You may obtain a
# copy of the License at:
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on
# an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied. See the License for the
# specific language governing permissions and limitations
# under the License.
#

# Reference to jQuery not in global scope
$ = jQuery

# Create context.
context = window.context ||= {}

# Create services.
context.sessionService ||= new mygps.service.SessionService( "../api/1/session" )
context.taskService ||= new mygps.service.TaskService( "../api/1/mygps/task" )

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
            bannerTemplate:     "/ssp/MyGPS/templates/banner.html"
            footerTemplate:     "/ssp/MyGPS/templates/footer.html"
            topMenuTemplate:     "/ssp/MyGPS/templates/topmenu.html"
            tasksTemplate:      "/ssp/MyGPS/templates/tasks.html"
            taskTemplate:       "/ssp/MyGPS/templates/task.html"
            taskDetailTemplate: "/ssp/MyGPS/templates/taskdetail.html"
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