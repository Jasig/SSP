namespace 'mygps.model'

	FormOption:
	
		class FormOption
		
			constructor: ( id, label, value ) ->
				@id = ko.observable( id )
				@label = ko.observable( label )
				@value = ko.observable( value )
				
			@createFromTransferObject: ( formOptionTO ) ->
				return new FormOption( formOptionTO.id, formOptionTO.label, formOptionTO.value )
			
			@toTransferObject: ( formOption ) ->
				formOptionTO =
					id: formOption.id()
					label: formOption.label()
					value: formOption.value()
				return formOptionTO