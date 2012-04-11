namespace 'mygps.service'

	AbstractService:
	
		class AbstractService
			
			constructor: ( baseURL ) ->
				@baseURL = baseURL
				
			createURL: ( value ) ->
				return "#{ @baseURL ? '' }#{ value }"
