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

	TaskService:
	
		class TaskService extends mygps.service.AbstractService
			
			constructor: ( baseURL ) ->
				super( baseURL )
			
			createCustom: ( name, description, callbacks ) ->
				$.ajax(
					url: @createURL( "/createCustom?name=#{ encodeURIComponent( name ) }&description=#{ encodeURIComponent( description ) }" )
					dataType: "json"
					type: "POST"
					success: ( result ) ->
						task = mygps.model.Task.createFromTransferObject( result )
						callbacks?.result?( task )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			createForChallengeReferral: ( challengeId, challengeReferralId, callbacks ) ->
				$.ajax(
					url: @createURL( "/createForChallengeReferral?challengeId=#{ challengeId }&challengeReferralId=#{ challengeReferralId }" )
					dataType: "json"
					type: "POST"
					success: ( result ) ->
						task = mygps.model.Task.createFromTransferObject( result )
						callbacks?.result?( task )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			delete: ( taskId, callbacks ) ->
				$.ajax(
					url: @createURL( "/delete?taskId=#{ taskId }" )
					dataType: "json"
					type: "DELETE"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			email: ( emailAddress, callbacks ) ->
				$.ajax(
					url: @createURL( "/email?emailAddress=#{ emailAddress }" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			getAll: ( callbacks ) ->
				$.ajax(
					url: @createURL( "/getAll" )
					dataType: "json"
					success: ( result ) ->
						tasks = for task in result
							mygps.model.Task.createFromTransferObject( task )
						callbacks?.result?( tasks )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			mark: ( taskId, complete, callbacks ) ->
				$.ajax(
					url: @createURL( "/mark?taskId=#{ taskId }&complete=#{ complete }" )
					dataType: "json"
					type: "PUT"
					success: ( result ) ->
						task = mygps.model.Task.createFromTransferObject( result )
						callbacks?.result?( task )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
			
			print: ( callbacks ) ->
				window.open( @createURL( "/print" ) )
				result( true )
				###
				$.ajax(
					url: @createURL( "/print" )
					dataType: "json"
					success: ( result ) ->
						callbacks?.result?( result )
					error: ( fault ) ->
						callbacks?.fault?( fault )
				)
				###
