(($) ->

	namespace "mygps.enumeration"
	
		TaskFilter:
		
			class TaskFilter
				
				constructor: ( @name, @filterFunction ) ->
				
				@COMPLETED:
					new TaskFilter( 
						"Completed",
						( task ) -> task.completed()
					)
				@ALL: 
					new TaskFilter( 
						"All",
						( task ) -> true
					)
				@ACTIVE:
					new TaskFilter( 
						"Active",
						( task ) -> not task.completed()
					)
				
				@enumerators: () -> [ TaskFilter.ALL, TaskFilter.ACTIVE, TaskFilter.COMPLETED ]

)(jQuery);