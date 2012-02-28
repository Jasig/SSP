namespace 'ssp.controller'

	MainViewController:
	
		class MainViewController
		
			constructor: ( $route, $location ) ->
				@route = $route;
				@location = $location;
	   
				@route.when '/student', template: 'ssp/partials/student.html', controller: ssp.controller.AppController
				
	MainViewController.$inject = ['$route', '$location']