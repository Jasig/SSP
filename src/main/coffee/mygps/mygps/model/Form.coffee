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

	Form:
	
		class Form
		
			constructor: ( id, label, sections, completed ) ->
				@id = ko.observable( id )
				@label = ko.observable( label )
				@sections = ko.observableArray( sections )
				@completed = ko.observable( completed )
				
				@valid = ko.dependentObservable( @evaluateValid, this )
				
				section.form( this ) for section in @sections()
				
			evaluateValid: () ->
				return not _.any( @sections(), ( section ) -> not section.valid() )
			
			validate: () ->
				section.validate() for section in @sections()
				return
			
			@createFromTransferObject: ( formTO ) ->
				if formTO.sections?
					sections = for section in formTO.sections 
						mygps.model.FormSection.createFromTransferObject( section )
				return new Form( formTO.id, formTO.label, sections, formTO.completed )
				
			@toTransferObject: ( form ) ->
				if form.sections()
					sections = for section in form.sections()
						mygps.model.FormSection.toTransferObject( section )
				formTO =
					id: form.id()
					label: form.label()
					sections: sections
				return formTO