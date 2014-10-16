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

	AbstractSessionViewModel:
		
		class AbstractSessionViewModel

			@APP_NAME_API_URL = "/ssp/api/1/mygps/home/appname"

			constructor: ( session ) ->
				@session = session
				@authenticated = ko.dependentObservable( @evaluateAuthenticated, this )
				@authenticatedPersonName = ko.dependentObservable( @authenticatedPersonName, this )
				@isnonstudent = ko.dependentObservable( @evaluateNonStudentPermission, this )
				@appName = ko.observable( null )

			load: () ->
				@loadAppName(@appName)
				return
				
			evaluateAuthenticated: () ->
				return @session?.authenticatedPerson()?;

			evaluateNonStudentPermission: () ->
				person = @session?.authenticatedPerson()
				if person? and person.permissions()?
					permissions = person.permissions()
					if permissions.indexOf("ROLE_PERSON_READ") isnt -1 or permissions.indexOf("ROLE_PERSON_FILTER") isnt -1
						return true
				return false

			authenticatedPersonName: () ->
				person = @session?.authenticatedPerson()
				if person?
					return "#{ person.firstName() } #{ person.lastName() }"
				return null

			loadAppName: (callback) ->
				defaultAppName = "Back to SSP"
				$.ajax({
					type: "GET"
					url: @constructor.APP_NAME_API_URL
					success: (result) ->
						if result isnt null and result.replace(/^\s+|\s+$/g, "") isnt ""
							callback("Back to " + result)
						else
							callback(defaultAppName)
					error: (fault) ->
						callback(defaultAppName)
				})