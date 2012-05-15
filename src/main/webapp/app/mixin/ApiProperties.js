Ext.define('Ssp.mixin.ApiProperties', {	
	Extend: 'Ext.Component',
	
	initComponent: function(){
		this.callParent(arguments);
	},
	
	getContext: function() {
		var apiVersion = "1";
	    var base = document.getElementsByTagName('base')[0];
	    if (base && base.href && (base.href.length > 0)) {
	        base = base.href;
	    } else {
	        base = document.URL;
	    }
	    return base.substr(0, base.indexOf("/", base.indexOf("/", base.indexOf("//") + 2) + 1)) + '/api/' + apiVersion + '/';
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
		Ext.Ajax.request({
			url: args.url,
			method: args.method,
			headers: { 'Content-Type': 'application/json' },
			jsonData: args.jsonData || '',
			success: args.successFunc,
			failure: this.handleError
		}, args.scope || this);		
	},
	
	handleError: function(response) {
		var msg = 'Status Error: ' + response.status + ' - ' + response.statusText;
		Ext.Msg.alert('SSP Error', msg);								
	}
});