(($) ->

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

)(jQuery);