Ext.define('Ssp.controller.ApplicationEventsController', {
	extend: 'Ext.Base',
	config: {
		app: null
	},
	
	constructor: function(config){
		this.initConfig(config);
		return this.callParent(arguments);
	},
	
	setApplication: function(app){
		this.app = app;
	},	
	
	getApplication: function(){
		return this.app;
	},
	
	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	assignEvent: function( args ){
		if ( !this.getApplication().hasListener(args.eventName) )
		{
			this.getApplication().addListener(args.eventName, args.callBackFunc, args.scope);
		}
	},

	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	removeEvent: function( args ){
		if ( this.getApplication().hasListener( args.eventName ))
		{
			this.getApplication().removeListener( args.eventName, args.callBackFunc, args.scope );
		}
	}
});