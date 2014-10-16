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

namespace 'mygps.model'

	Task:
	
		class Task
		
			constructor: ( id, type, name, description, link, details, dueDate, completed, deletable, challengeId, challengeReferralId ) ->
				@id = ko.observable( id )
				@type = ko.observable( type )
				@name = ko.observable( name )
				@description = ko.observable( description )
				@details = ko.observable( details )
				@dueDate = ko.observable( dueDate )
				@completed = ko.observable( completed )
				@link = ko.observable( link )
				@deletable = ko.observable( deletable )
				@challengeId = ko.observable( challengeId )
				@challengeReferralId = ko.observable( challengeReferralId )
				
			@createFromTransferObject: ( taskTO ) ->
				# TODO: Extract to utility function.
				parseDate = ( msSinceEpoch ) ->
					if msSinceEpoch?
						d = new Date( msSinceEpoch )
						# Gets date back to midnight browser-local time. msSinceEpoch is
						# a ISO8601 date without a timezone, so is interpreted in UTC.
						# If then rendered in browser local time (which is what happens for
						# all date rendering both in MyGPS and SSP-proper), the output would
						# reflect the previous calendar day for most SSP deployments.
						d.setTime(d.getTime() + (d.getTimezoneOffset() * 60 * 1000))
						d
					else null
				return new Task( taskTO.id, taskTO.type, taskTO.name, taskTO.description, taskTO.htmlLink, taskTO.details, parseDate( taskTO.dueDate ), taskTO.completed, taskTO.deletable, taskTO.challengeId, taskTO.challengeReferralId )