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
				
			load: () ->
				super()
				return
				
			evaluateCanContactCoach: () ->
				return @session?.authenticatedPerson()?.coach()?

			evaluatePersonId: () ->
				return @session?.authenticatedPerson()?.id()

			evaluateMapUrl: () ->
				return "/ssp/api/1/person/" + @personId() + "/map/plan/print"
           
			getCurrentMap = (personId, callback) ->
                $.ajax({
	                type: "GET"
	                url: "/ssp/api/1/person/" + personId + "/map/plan/current"
	                dataType: "json"
	                success: (result) ->
                        callback(result)
	                error: (fault) ->
		                callback(false)
                })

			evaluateShowMap: () ->
				if @session?.authenticatedPerson()?.id()?
                    getCurrentMap(@session?.authenticatedPerson()?.id(), @showMap)

			ko.bindingHandlers.popupWindow = init: (element, valueAccessor) ->
				values = ko.utils.unwrapObservable(valueAccessor())
				$(element).click ->
					window.open(ko.utils.unwrapObservable(values.url), "ChildWindow", "height=" + values.height + ",width=" + values.width)

				false