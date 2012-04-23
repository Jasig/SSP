(($) ->

	namespace 'mygps.model'
	
		Task:
		
			class Task
			
				constructor: ( id, type, name, description, details, dueDate, completed, deletable, challengeId, challengeReferralId ) ->
					@id = ko.observable( id )
					@type = ko.observable( type )
					@name = ko.observable( name )
					@description = ko.observable( description )
					@details = ko.observable( details )
					@dueDate = ko.observable( dueDate )
					@completed = ko.observable( completed )
					@deletable = ko.observable( deletable )
					@challengeId = ko.observable( challengeId )
					@challengeReferralId = ko.observable( challengeReferralId )
					
				@createFromTransferObject: ( taskTO ) ->
					# TODO: Extract to utility function.
					parseDate = ( msSinceEpoch ) ->
						if msSinceEpoch?
							new Date( msSinceEpoch ) 
						else null
					return new Task( taskTO.id, taskTO.type, taskTO.name, taskTO.description, taskTO.details, parseDate( taskTO.dueDate ), taskTO.completed, taskTO.deletable, taskTO.challengeId, taskTO.challengeReferralId )

)(jQuery);