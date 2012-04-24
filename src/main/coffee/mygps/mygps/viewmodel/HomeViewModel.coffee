namespace 'mygps.viewmodel'

	HomeViewModel:
		
		class HomeViewModel extends mygps.viewmodel.AbstractTasksViewModel
			
			constructor: ( session, taskService ) ->
				super( session, taskService )
				
				@canContactCoach = ko.dependentObservable( @evaluateCanContactCoach, this )
				
			load: () ->
				super()
				return
				
			evaluateCanContactCoach: () ->
				return @session?.authenticatedPerson()?.coach()?;
