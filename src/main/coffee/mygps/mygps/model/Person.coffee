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

	Person:
	
		class Person
		
			constructor: ( id, firstName, lastName, phoneNumber, emailAddress, photoURL, coach, permissions ) ->
				@id = ko.observable( id )
				@firstName = ko.observable( firstName )
				@lastName = ko.observable( lastName )
				@phoneNumber = ko.observable( phoneNumber )
				@photoURL = ko.observable( photoURL )
				@coach = ko.observable( coach )
				@permissions = ko.observable( permissions )
				
			@createFromTransferObject: ( personTO ) ->
				if personTO.coach?
					coach = mygps.model.Person.createFromTransferObject( personTO.coach )
				return new Person( personTO.id, personTO.firstName, personTO.lastName, personTO.phoneNumber, personTO.emailAddress, personTO.photoURL, coach )