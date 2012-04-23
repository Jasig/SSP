(($) ->

	namespace 'mygps.model'
	
		Form:
		
			class Form
			
				constructor: ( id, label, sections ) ->
					@id = ko.observable( id )
					@label = ko.observable( label )
					@sections = ko.observableArray( sections )
					
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
					return new Form( formTO.id, formTO.label, sections )
					
				@toTransferObject: ( form ) ->
					if form.sections()
						sections = for section in form.sections()
							mygps.model.FormSection.toTransferObject( section )
					formTO =
						id: form.id()
						label: form.label()
						sections: sections
					return formTO

)(jQuery);