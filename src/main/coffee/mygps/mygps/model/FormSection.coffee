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

	FormSection:
	
		class FormSection
		
			constructor: ( id, label, questions ) ->
				@id = ko.observable( id )
				@label = ko.observable( label )
				@questions = ko.observableArray( questions )
				
				@valid = ko.dependentObservable( @evaluateValid, this )
				
				@form = ko.observable( null )
				
				question.section( @ ) for question in @questions()
				
			evaluateValid: () ->
				return not _.any( @questions(), ( question ) -> not question.valid() )
			
			validate: () ->
				question.validate() for question in @questions()
				return
				
			@createFromTransferObject: ( formSectionTO ) ->
				if formSectionTO.questions?
					questions = for question in formSectionTO.questions 
						mygps.model.FormQuestion.createFromTransferObject( question )
				return new FormSection( formSectionTO.id, formSectionTO.label, questions )
			
			@toTransferObject: ( formSection )->
				if formSection.questions()
					questions = for question in formSection.questions()
						mygps.model.FormQuestion.toTransferObject( question )
				formSectionTO =
					id: formSection.id()
					label: formSection.label()
					questions: questions
				return formSectionTO