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

	FormQuestion:
	
		class FormQuestion
		
			constructor: ( id, label, type, value, values, options, readOnly, required, maximumLength, visibilityExpression, availabilityExpression, validationExpression ) ->
				@id = ko.observable( id )
				@label = ko.observable( label )
				@type = ko.observable( type )
				@value = ko.observable( value )
				@values = ko.observableArray( values or [] )
				@options = ko.observableArray( options or [] )
				@readOnly = ko.observable( readOnly )
				@required = ko.observable( required )
				@maximumLength = ko.observable( maximumLength )
				@visibilityExpression = ko.observable( visibilityExpression )
				@availabilityExpression = ko.observable( availabilityExpression )
				@validationExpression = ko.observable( validationExpression )
				
				@labelWithIndicator = ko.observable( if required then label + ' *' else label )
				@section = ko.observable( null )
				@valid = ko.observable( true )
				@enabled = ko.dependentObservable( @evaluateEnabled, this )
				@visible = ko.dependentObservable( @evaluateVisible, this )
			
			evaluateEnabled: () ->
				if @readOnly()
					return false
				if @availabilityExpression()?
					return @executeExpression( @availabilityExpression() )
				return true
			
			evaluateVisible: () ->
				if @visibilityExpression()?
					return @executeExpression( @visibilityExpression() )
				return true
				
			executeExpression: ( expression ) ->
				# Helper methods that may be called within the specified expression.
				window['getQuestionById'] = ( id ) =>
					return _.detect( @section()?.questions(), ( question ) -> question.id() is id )
				
				window['hasValueForQuestionId'] = ( value, questionId ) =>
					question = getQuestionById( questionId )
					if question?
						if question.type() is "checklist"
							return _.include( question.values(), value )
						else
							return question.value() is value
					return false
				
				window['hasValue'] = ( expectedValue ) =>
					return @value() is expectedValue
				
				window['isChecked'] = () =>
					# NOTE: Intentionally relying on 'truthy' behavior.
					if @value() then true else false
				
				return eval( expression )
			
			validate: () ->
				valid = true;
				if @enabled() and @visible()
					if @required() and ( @value() is null or @value() is "" )
						valid = false
					if valid and @validationExpression()?
						valid = @executeExpression( @validationExpression() )
				@valid( valid )
				return
			
			@createFromTransferObject: ( formQuestionTO ) ->
				if formQuestionTO.options?
					options = for option in formQuestionTO.options 
						mygps.model.FormOption.createFromTransferObject( option )
				return new FormQuestion( formQuestionTO.id, formQuestionTO.label, formQuestionTO.type, formQuestionTO.value, formQuestionTO.values, options, formQuestionTO.readOnly, formQuestionTO.required, formQuestionTO.maximumLength, formQuestionTO.visibilityExpression, formQuestionTO.availabilityExpression, formQuestionTO.validationExpression )
			
			@toTransferObject: ( formQuestion )->
				if formQuestion.options()
					options = for option in formQuestion.options()
						mygps.model.FormOption.toTransferObject( option )
				formQuestionTO =
					id: formQuestion.id()
					label: formQuestion.label()
					type: formQuestion.type()
					value: formQuestion.value()
					values: formQuestion.values()
					options: options
					readOnly: formQuestion.readOnly()
					required: formQuestion.required()
					maximumLength: formQuestion.maximumLength()
					visibilityExpression: formQuestion.visibilityExpression()
					availabilityExpression: formQuestion.availabilityExpression()
					validationExpression: formQuestion.validationExpression()
				return formQuestionTO
