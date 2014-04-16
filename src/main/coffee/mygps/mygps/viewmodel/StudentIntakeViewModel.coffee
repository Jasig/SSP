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

	StudentIntakeViewModel:
		
		class StudentIntakeViewModel extends mygps.viewmodel.AbstractSessionViewModel
			
			constructor: ( session, studentIntakeService ) ->
				super( session )
				@studentIntakeService = studentIntakeService
				
				@form = ko.observable( null )
				@savingForm = ko.observable( false )
				@invalid = ko.observable( false )
				@currentSectionIndex = ko.observable( 0 )
				@currentSection = ko.dependentObservable( @evaluateCurrentSection, this )
				@hasPreviousSection = ko.dependentObservable( @evaluateHasPreviousSection, this )
				@hasNextSection = ko.dependentObservable( @evaluateHasNextSection, this )
				@completed = ko.dependentObservable( @evaluateCompleted, this )
			
			load: () ->
				super()
				@loadForm()
				return
			
			evaluateCurrentSection: () ->
				return @form()?.sections()?[ @currentSectionIndex() ]

			evaluateCompleted: () ->
				return @form()?.completed()
			
			evaluateHasPreviousSection: () ->
				if @form()?
					return @currentSectionIndex() > 0
				return false
			
			evaluateHasNextSection: () ->
				if @form()?
					return @currentSectionIndex() < @form().sections().length - 1
				return false
			
			loadForm: () ->
				@studentIntakeService.getForm(
					result: ( result ) =>
						@form( result )
					fault: ( fault ) =>
						alert( $.parseJSON(fault.responseText)?.message ? fault.responseText )
				)
				return
			
			saveForm: ( callbacks ) ->
				@currentSection().validate()
				if @currentSection().valid()
					@invalid( false )
					@savingForm( true )
					@studentIntakeService.saveForm(
						@form(),
						result: ( result ) =>
							@savingForm( false )
							callbacks?.result?( result )
						fault: ( fault ) =>
							@savingForm( false )
							alert( 'There was an error with your submission.  Please contact the system Administrator' )
							callbacks?.fault?( fault )
					)
				else
					@invalid( true )
				return
			
			moveToPreviousSection: () ->
				if ( @hasPreviousSection() )
					@currentSectionIndex( @currentSectionIndex() - 1 )
					@invalid( false )
					return true
				return false
			
			moveToNextSection: () ->
				if ( @hasNextSection() )
					@currentSection().validate()
					if @currentSection().valid()
						@invalid( false )
						@currentSectionIndex( @currentSectionIndex() + 1 )
						return true
					else
						@invalid( true )
				return false
