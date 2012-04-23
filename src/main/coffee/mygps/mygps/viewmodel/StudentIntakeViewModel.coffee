(($) ->

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
				
				load: () ->
					super()
					@loadForm()
					return
				
				evaluateCurrentSection: () ->
					return @form()?.sections()?[ @currentSectionIndex() ]
				
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
								alert( fault.responseText )
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

)(jQuery);