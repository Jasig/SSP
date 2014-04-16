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

	AbstractTasksViewModel:
		
		class AbstractTasksViewModel extends mygps.viewmodel.AbstractSessionViewModel
			
			constructor: ( session, taskService ) ->
				super( session )
				@taskService = taskService
				
				@tasks = ko.observableArray( [] )
				@taskFilters = ko.observableArray( mygps.enumeration.TaskFilter.enumerators() )
				@selectedTaskFilter = ko.observable( mygps.enumeration.TaskFilter.ACTIVE )
				@filteredTasks = ko.dependentObservable( @filterTasks, this )
				@printingTasks = ko.observable( false )	
				@emailingTasks = ko.observable( false )
				
			load: () ->
				super()
				@loadAllTasks()
				return
				
			formatDate: ( value ) ->
				if value?
					now = new Date()
					if value.getFullYear() is now.getFullYear()
						return "#{ value.getMonth() + 1 }/#{ value.getDate() }"
					else
						return "#{ value.getMonth() + 1 }/#{ value.getDate() }/#{ ( "" + value.getFullYear() ).substring(2) }"
				return ""
			
			filterTasks: () ->
				return _.select(
					@tasks(),
					@selectedTaskFilter().filterFunction
				)
				
			loadAllTasks: () ->
				@taskService.getAll(
					result: ( result ) =>
						@tasks( result )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return
				
			selectTaskFilter: ( taskFilter ) ->
				@selectedTaskFilter( taskFilter )
				return
				
			createCustomTask: ( name, description, callbacks ) ->
				@taskService.createCustom(
					name,
					description,
					result: ( result ) =>
						@tasks.push( result )
						callbacks?.result?( result )
					fault: ( fault ) =>
						alert( fault.responseText )
						callbacks?.fault?( fault )
				)
				return
			
			createTaskForChallengeReferral: ( challenge, challengeReferral ) ->
				@taskService.createForChallengeReferral(
					challenge.id(),
					challengeReferral.id(),
					result: ( result ) =>
						@tasks.push( result )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return

			createTaskForChallengeReferral: ( challenge, challengeReferral, callbacks ) ->
				@taskService.createForChallengeReferral(
					challenge.id(),
					challengeReferral.id(),
					result: ( result ) =>
						@tasks.push( result )
						callbacks?.result?( result )
					fault: ( fault ) =>
						alert( fault.responseText )
						callbacks?.fault?( fault )
				)
				return
				
			markTask: ( task, complete ) ->
				@taskService.mark(
					task.id(),
					complete,
					result: ( result ) =>
						task.completed( complete )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return
				
			deleteTask: ( task ) ->
				@taskService.delete(
					task.id(),
					result: ( result ) =>
						@tasks.remove( task )
					fault: ( fault ) =>
						alert( fault.responseText )
				)
				return

			deleteTask: ( task, callbacks ) ->
				@taskService.delete(
					task.id(),
					result: ( result ) =>
						@tasks.remove( task )
						callbacks?.result?( result )
					fault: ( fault ) =>
						alert( fault.responseText )
						callbacks?.fault?( fault )
				)
				return
				
			printTasks: () ->
				@printingTasks( true )
				@taskService.print(
					result: ( result ) =>
						@printingTasks( false )
					fault: ( fault ) =>
						alert( fault.responseText )
						@printingTasks( false )
				)
				return
			
			emailTasks: ( emailAddress ) ->
				@emailingTasks( true )
				@taskService.email(
					emailAddress,
					result: ( result ) =>
						@emailingTasks( false )
					fault: ( result ) =>
						alert( fault.responseText )
						@emailingTasks( false )
				)
				return
				
			collectEmailAddress: () ->
				apprise('E-mail address:',
					{ 'input': true,
					'validation': (value) =>
						# Regex from:  http://www.regular-expressions.info/email.html
						if /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value) or value == ''
							return 'true'
						else
							return 'invalid email address'
					},
					(result) => 
						if result
							@emailTasks( result ) 
				)
				return
