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

namespace 'mygps.viewmodel'

	HomeViewModel:
		
		class HomeViewModel extends mygps.viewmodel.AbstractTasksViewModel

			@CONTACT_COACH_TOOL: "CONTACT_COACH"
			@SELF_HELP_GUIDE_TOOL: "SELF_HELP_GUIDES"
			@RESOURCES_TOOL: "SEARCH"
			@MAP_TOOL: "MAP"
			@TOOLS = [@CONTACT_COACH_TOOL,@SELF_HELP_GUIDE_TOOL,@RESOURCES_TOOL,@MAP_TOOL]
			@MAP_PRINT_CURRENT_API_URL = "/ssp/api/1/mygps/plan/print"
			@MAP_CURRENT_API_URL = "/ssp/api/1/mygps/plan/current"
			@APP_NAME_API_URL = "/ssp/api/1/mygps/home/appname"
			@WELCOME_MESSAGE_API_URL = "/ssp/api/1/mygps/home/welcome"
			@TOOLS_LIST_API_URL = "/ssp/api/1/mygps/home/tools"


			constructor: ( session, taskService ) ->
				super( session, taskService )
				@personId = ko.dependentObservable(@evaluatePersonId, this)
				@appName = ko.observable( null )
				@welcomeMessage = ko.observable( null )
				@toolsList = ko.observableArray( [] )
				@showSelfHelpGuides = ko.dependentObservable( @evaluateShowSelfHelpGuides, this )
				@showContactCoach = ko.dependentObservable( @evaluateShowContactCoach, this )
				@showResources = ko.dependentObservable( @evaluateShowResources, this )
				@mapToolEnabled = ko.dependentObservable( @evaluateMapToolEnabled, this )
				@mapLoadAttempted = ko.observable( false )
				@currentMapExists = ko.observable( false )
				@showMap = ko.dependentObservable( @evaluateShowMap, this )
				@showAnyTools = ko.dependentObservable( @evaluateShowAnyTools, this)
				@mapPrintUrl = @constructor.MAP_PRINT_CURRENT_API_URL

			load: () ->
				super()
				@loadContent()
				return

			isToolEnabled: (toolName) ->
				@toolsList().indexOf(toolName.toUpperCase()) isnt -1

			evaluateShowAnyTools: () ->
				@showContactCoach() or @showSelfHelpGuides() or @showResources() or @showMap()

			evaluateShowContactCoach: () ->
				@session?.authenticatedPerson()?.coach()? and @isToolEnabled(@constructor.CONTACT_COACH_TOOL)

			evaluatePersonId: () ->
				@session?.authenticatedPerson()?.id()

			evaluateMapToolEnabled: () ->
				@isToolEnabled(@constructor.MAP_TOOL)

			evaluateShowMap: (lazyLoad) ->
				if ( @mapToolEnabled() is true and @currentMapExists() isnt true )
					@loadMapExistenceOnce(@currentMapExists)
				@currentMapExists()

			evaluateShowSelfHelpGuides: () ->
				@isToolEnabled(@constructor.SELF_HELP_GUIDE_TOOL)

			evaluateShowResources: () ->
				@isToolEnabled(@constructor.RESOURCES_TOOL)

			loadMapExistenceOnce: (callback) ->
				if @session?.initialized() and @personId()? and @mapLoadAttempted() isnt true
					@mapLoadAttempted(true)
					$.ajax({
						type: "GET"
						url: @constructor.MAP_CURRENT_API_URL
						dataType: "json"
						success: (result) ->
							if result isnt null and result isnt {} and result isnt []
								callback(true)
							else
								callback(false)
						error: (fault) ->
								callback(false)
					})


			loadAppName: (callback) ->
				defaultAppName = "Back to SSP"
				$.ajax({
					type: "GET"
					url: @constructor.APP_NAME_API_URL
					success: (result) ->
						if result isnt null and result.replace(/^\s+|\s+$/g, "") isnt ""
							callback(result)
						else
							callback(defaultAppName)
					error: (fault) ->
						callback(defaultAppName)
				})

			loadWelcomeMessage: (callback) ->
				defaultWelcomeMessage = "<h2>Welcome</h2> \n<p>This self help tool will assist you in identifying and overcoming challenges " +
				"or barriers to your success at this college. Please use the Self Help Guides to begin the process of identifying " +
 				"the challenges you might face, and discovering the solutions available to meet those challenges. " +
				"The tool will assist you in building a Personal Road Map that will guide you on your journey to success. " +
				"Good luck on that journey!</p> \n";
				$.ajax({
					type: "GET"
					url: @constructor.WELCOME_MESSAGE_API_URL
					success: (result) ->
						if result isnt null
							trimmed = result.replace(/^\s+|\s+$/g, "")
							callback(if trimmed is "" then null else trimmed)
						else
							callback(defaultWelcomeMessage)
					error: (fault) ->
						callback(defaultWelcomeMessage)
				})

			loadToolsList: (callback) ->
				$.ajax({
					type: "GET"
					url: @constructor.TOOLS_LIST_API_URL
					success: (result) =>
						if result isnt null
							trimmed = result.replace(/^\s+|\s+$/g, "")
							if trimmed isnt ""
								callback(toolName.trim().toUpperCase() for toolName in (trimmed.split ","))
							else callback([])
						else
							callback([])
					error: (fault) ->
						callback([])
				})

			resetContent: () ->
				# Clear toolsList first b/c if we clear other fields, it might cause observable functions to
				# fire while the toolsList is in a stale state, e.g. when you use the back-button to return
				# to this page. That's mostly a non-issue except for MAP, which we only attempt to load once.
				# So if cascading observer functions happen to cause it to load before we get around to updating
				# the toolsList, you might still see the MAP tool link on a back-button nav, even if MAP has been
				# disabled since you last visited the page.
				#
				# On a more mechanical note, would be nice if we didn't have to know what the default should
				# be to reset to: http://anasnakawa.com/posts/resetting-knockout-observables/
				@toolsList( [] )
				@appName( null )
				@welcomeMessage( null )
				@welcomeMessage( null )
				@mapLoadAttempted(false)
				@currentMapExists(false)

			loadContent: () ->
				@resetContent()
				@loadAppName(@appName)
				@loadWelcomeMessage(@welcomeMessage)
				@loadToolsList(@toolsList)
				return

			ko.bindingHandlers.popupWindow = init: (element, valueAccessor) ->
				values = ko.utils.unwrapObservable(valueAccessor())
				$(element).click ->
					window.open(ko.utils.unwrapObservable(values.url), "ChildWindow", " height=" + values.height + ", width=" + values.width + ", resizable=1, scrollbars=1")

				false
