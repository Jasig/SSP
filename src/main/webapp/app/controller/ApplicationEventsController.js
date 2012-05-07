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
	}
});