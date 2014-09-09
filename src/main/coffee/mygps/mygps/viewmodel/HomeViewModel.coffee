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

			constructor: ( session, taskService ) ->
				super( session, taskService )
				
				@canContactCoach = ko.dependentObservable( @evaluateCanContactCoach, this )
				@currentMapJson = ko.dependentObservable(@evaluateShowMap, this)
				@showMap = ko.observable(false)
				@personId = ko.dependentObservable(@evaluatePersonId, this)
				@mapUrl = ko.dependentObservable(@evaluateMapUrl, this)
				@appName = ko.dependentObservable(@evaulateAppName, this)
				@welcomeMessage = ko.dependentObservable(@evaluateWelcomeMessage, this)
				@toolsList = ko.dependentObservable(@evaluateTools, this)
				
			load: () ->
				super()
				return
				
			evaluateCanContactCoach: () ->
				return @session?.authenticatedPerson()?.coach()?

			evaluatePersonId: () ->
				return @session?.authenticatedPerson()?.id()

			evaluateMapUrl: () ->
				return "/ssp/api/1/mygps/plan/print"
           
			getCurrentMap = (personId, callback) ->
                $.ajax({
	                type: "GET"
	                url: "/ssp/api/1/mygps/plan/current"
	                dataType: "json"
	                success: (result) ->
                        callback(result)
	                error: (fault) ->
		                callback(false)
                })

			getAppName = (callback) ->
                $.ajax({
	                type: "GET"
	                url: "/ssp/api/1/mygps/home/appname"
	                success: (result) ->
                        if result isnt null and result.replace(/^\s+|\s+$/g, "") isnt ""
                            callback(result)
                        else
                            callback(false)
	                error: (fault) ->
                        callback(false)
                })

			getWelcomeMessage = (callback) ->
                $.ajax({
	                type: "GET"
	                url: "/ssp/api/1/mygps/home/welcome"
	                success: (result) ->
                        if result isnt null and result.replace(/^\s+|\s+$/g, "") isnt ""
                            callback(result)
                        else
                            callback(false)
	                error: (fault) ->
                        callback(false)
                })

			getToolsList = (callback) ->
                $.ajax({
	                type: "GET"
	                url: "/ssp/api/1/mygps/home/appname"
	                success: (result) ->
                        if result isnt null and result.replace(/^\s+|\s+$/g, "") isnt ""
                            callback(result)
                        else
                            callback(false)
	                error: (fault) ->
                        callback(false)
                })

			evaluateShowMap: () ->
                if @session?.authenticatedPerson()?.id()?
	                getCurrentMap(@session?.authenticatedPerson()?.id(), @showMap)

			evaulateAppName: () ->
                getAppName(@appName)
                if @appName is false
	                @appName = null

			evaluateWelcomeMessage: () ->
                getWelcomeMessage(@welcomeMessage)
                if (@welcomeMessage is false or (@welcomeMessage.indexOf("<h" < 0) and @welcomeMessage.indexOf("<p" < 0)))
	                @welcomeMessage = null

			evaluateTools: () ->
                getToolsList(@toolsList)
                if @toolsList is false
	                @toolsList = null
                else
	                tools = ["<li><a href=\"guides.html\">Self Help Guides</a></li> \n",
	                "<li style=\"display: none;\" data-bind=\"visible: canContactCoach\"><a href=\"contact.html\">Contact Your Coach</a></li> \n",
	                "<li><a href=\"search.html\">Search for Resources</a></li> \n",
	                "<li style=\"display: none;\" data-bind=\"visible: showMap\"><a href=\"#\" data-bind=\"popupWindow: { url: mapUrl, height: '550px', width: '1010px' }\">View My Map</a></li> \n"]
	                generatedToolList = ""

	                if @toolsList.indexOf "Self Help" > -1
	                    generatedToolList = generatedToolList + tools[0]

	                if @toolsList.indexOf "Contact" > -1
	                    generatedToolList = generatedToolList + tools[1]

	                if @toolsList.indexOf "Search" > -1
	                    generatedToolList = generatedToolList + tools[2]

	                if @toolsList.indexOf "Map" > -1
	                    generatedToolList = generatedToolList + tools[3]

	                @toolsList = generatedToolList


			ko.bindingHandlers.popupWindow = init: (element, valueAccessor) ->
				values = ko.utils.unwrapObservable(valueAccessor())
				$(element).click ->
					window.open(ko.utils.unwrapObservable(values.url), "ChildWindow", " height=" + values.height + ", width=" + values.width + ", resizable=1, scrollbars=1")

				false
