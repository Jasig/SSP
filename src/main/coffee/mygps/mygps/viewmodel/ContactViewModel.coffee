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

	ContactViewModel:
		
		class ContactViewModel extends mygps.viewmodel.AbstractSessionViewModel
			
			constructor: ( session, messageService ) ->
				super( session )
				@messageService = messageService
				
				@subject = ko.observable( null )
				@message = ko.observable( null )
				@contactingCoach = ko.observable( false )
				
			load: () ->
				super()
				return
				
			contactCoach: ( subject, message, callbacks ) ->
				@contactingCoach( true )
				@messageService.contactCoach(
					subject,
					message,
					result: ( result ) =>
						@contactingCoach( false )
						callbacks?.result?( result )
					fault: ( fault ) =>
						if fault.responseText.indexOf("org.hibernate.exception.ConstraintViolationException") != -1 
							alert "You must provide a Subject and a Message before it can be sent to your coach."
						else
							alert fault.responseText
						
						@contactingCoach( false )
						callbacks?.fault?( fault )
				)
				return
