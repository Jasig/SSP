Ext.define('Ssp.mixin.ApiProperties', {	
	Extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiUrlStore: 'apiUrlStore' 
    },
    statics: {
    	getBaseAppUrl: function(){
    		var apiVersion = "1";
    	    var base = document.getElementsByTagName('base')[0];
    	    if (base && base.href && (base.href.length > 0)) {
    	        base = base.href;
    	    } else {
    	        base = document.URL;
    	    }
    	    return base.substr(0, base.indexOf("/", base.indexOf("//") + 2) + 1) + Ext.Loader.getPath('ContextName') + '/api/' + apiVersion + '/';
    	}
    },
    
	initComponent: function(){
		this.callParent(arguments);
	},
	
	getContext: function() {
		return Ssp.mixin.ApiProperties.getBaseAppUrl();
	},
	
	createUrl: function(value){
		return this.getContext() + value;
	},
	
	getPagingSize: function(){
		return 10;
	},
	
	getProxy: function(url){
		var proxyObj = {
			type: 'rest',
			url: this.createUrl(url),
			actionMethods: {
				create: "POST", 
				read: "GET", 
				update: "PUT", 
				destroy: "DELETE"
			},
			reader: {
				type: 'json',
				root: 'rows',
				totalProperty: 'results',
				successProperty: 'success',
				message: 'message'
			},
		    writer: {
		        type: 'json',
		        successProperty: 'success'
		    }
		};
		return proxyObj;
	},
	
	/*
	 * @args - {}
	 *    url - url of the request
	 *    method - 'PUT', 'POST', 'GET', 'DELETE'
	 *    jsonData - data to send
	 *    successFunc - success function
	 *    scope - scope
	 */
	makeRequest: function(args){
		var contentType = "application/json";
		var errorHandler = this.handleError;
		if (args.failure != null)
		{
			errorHandler = args.failure;
		}
		if ( args.contentType != null)
		{
			contentType = args.contentType;
		}
		Ext.Ajax.request({
			url: args.url,
			method: args.method,
			headers: { 'Content-Type': contentType },
			jsonData: args.jsonData || '',
			success: args.successFunc,
			failure: errorHandler,
			scope: ((args.scope != null)? args.scope : this)
		},this);		
	},
	
	handleError: function(response) {
		var msg = 'Status Error: ' + response.status + ' - ' + response.statusText;
		var r = Ext.decode(response.responseText);

		if (r.message != null)
		{
			msg = msg + " " + r.message;
		}
		
		Ext.Msg.alert('SSP Error', msg);								
	},
	
	/*
	 * Returns the base url of an item in the system.
	 * @itemName - the name of the item to locate.
	 * 	the returned item is returned by name from the apiUrlStore.
	 */
	getItemUrl: function( itemName ){
		var record = this.apiUrlStore.findRecord('name', itemName);
		var url = "";
		if (record != null)
			url = record.get('url');
		return url;
	},
	
	getReporter: function(){
		return Ext.ComponentQuery.query('sspreport')[0];
	}
});