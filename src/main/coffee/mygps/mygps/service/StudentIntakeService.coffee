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

namespace 'mygps.service'

	StudentIntakeService:
	
		class StudentIntakeService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			getForm: ( callbacks ) ->
				$.ajax(
					url: @createURL( "/getForm" )
					dataType: "json"
					success: ( result ) ->
						form = mygps.model.Form.createFromTransferObject( result )
						callbacks?.result?( form )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
				
			saveForm: ( form, callbacks ) ->
				$.ajax(
					url: @createURL( "/" )
					type: "POST"
					data: JSON.stringify( mygps.model.Form.toTransferObject( form ) )
					contentType: "application/json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
