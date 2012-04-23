(($) ->

	namespace 'mygps.viewmodel'
	
		ContactViewModel:
			
			class ContactViewModel extends mygps.viewmodel.AbstractSessionViewModel
				
				constructor: ( session, messageService ) ->
					super( session )
					@messageService = messageService
					
					@subject = ko.observable( null )
					@message = ko.observable( null )
					@contactingCoach = ko.observable( false )
					
				load: () ->
					super()
					return
					
				contactCoach: ( subject, message, callbacks ) ->
					@contactingCoach( true )
					@messageService.contactCoach(
						subject,
						message,
						result: ( result ) =>
							@contactingCoach( false )
							callbacks?.result?( result )
						fault: ( fault ) =>
							if fault.responseText.indexOf("org.hibernate.exception.ConstraintViolationException") != -1 
								alert "You must provide a Subject and a Message before it can be sent to your coach."
							else
								alert fault.responseText
							
							@contactingCoach( false )
							callbacks?.fault?( fault )
					)
					return

)(jQuery);