Ext.define('Ssp.mixin.ApiProperties', {	
	Extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable' ],
    config: {
    	baseUrl: '',
    	baseApiUrl: ''
    },
    inject: {
    	apiUrlStore: 'apiUrlStore' 
    },
    statics: {
    	getBaseApiUrl: function(){
    		var apiVersion = "1";
    	    var base = document.getElementsByTagName('base')[0];
    	    if (base && base.href && (base.href.length > 0)) {
    	        base = base.href;
    	    } else {
    	        base = document.URL;
    	    }
    	    return base.substr(0, base.indexOf("/", base.indexOf("//") + 2) + 1) + Ext.Loader.getPath('ContextName') + '/api/' + apiVersion + '/';
    	},
    	
    	getBaseAppUrl: function(){
    		var apiVersion = "1";
    	    var base = document.getElementsByTagName('base')[0];
    	    if (base && base.href && (base.href.length > 0)) {
    	        base = base.href;
    	    } else {
    	        base = document.URL;
    	    }
    	    return base.substr(0, base.indexOf("/", base.indexOf("//") + 2) + 1) + Ext.Loader.getPath('ContextName');
    	}
    },
    
	initComponent: function(){
		var me=this;
		
		me.baseUrl = Ssp.mixin.ApiProperties.getBaseAppUrl();
		me.baseApiUrl = Ssp.mixin.ApiProperties.getBaseApiUrl();
			
		this.callParent(arguments);
	},
	
	getContext: function() {
		return Ssp.mixin.ApiProperties.getBaseAppUrl();
	},

	getAPIContext: function() {
		return Ssp.mixin.ApiProperties.getBaseApiUrl();
	},
	
	createUrl: function(value){
		return Ssp.mixin.ApiProperties.getBaseApiUrl() + value;
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
	makeRequest: function( args ){
		var me=this;
		var contentType = "application/json";
		var errorHandler = me.handleError;
		var scope = me;
		if (args.failureFunc != null)
		{
			errorHandler = args.failureFunc;
		}
		if ( args.contentType != null)
		{
			contentType = args.contentType;
		}
		if (args.scope != undefined && args.scope != null)
		{
			scope = args.scope;
		}
		Ext.Ajax.request({
			url: args.url,
			method: args.method,
			headers: { 'Content-Type': contentType },
			jsonData: args.jsonData || '',
			success: args.successFunc,
			failure: errorHandler,
			scope: scope
		},me);		
	},
	
	handleError: function( response ) {
		var me=this;
		var msg = 'Status Error: ' + response.status + ' - ' + response.statusText;
		var r;

		if (response.status==403)
		{
			Ext.Msg.confirm({
	   		     title:'Access Denied Error',
	   		     msg: "It looks like you are trying to access restricted information or your login session has expired. Would you like to login to continue working in SSP?",
	   		     buttons: Ext.Msg.YESNO,
	   		     fn: function( btnId ){
	   		    	if (btnId=="yes")
	   		    	{
	   		    		// force a login
	   		    		window.location.reload();
	   		    	}else{
	   		    		// force a login
	   		    		window.location.reload();
	   		    	}
	   		    },
	   		     scope: me
	   		});
		}
		
		// Handle call not found result
		if (response.status==404)
		{
			Ext.Msg.alert('SSP Error', msg);
		}

		if ( response.status==500 )
		{
			// Handle responseText is json returned from SSP
			if( response.responseText != null )
			{
				if ( response.responseText != "")
				{
					r = Ext.decode(response.responseText);
					if (r.message != null)
					{
						if ( r.message != "")
						{
							msg = msg + " " + r.message;
							Ext.Msg.alert('SSP Error', msg);							
						}
					}else{
						Ext.Msg.alert('Internal Server Error - 500', 'Unable to determine the source of this error. See logs for additional details.');
					}
				}
			}
		}		
		
		if ( response.status==200 )
		{
			// Handle responseText is json returned from SSP
			if( response.responseText != null )
			{
				if ( response.responseText != "")
				{
					r = Ext.decode(response.responseText);
					if (r.message != null)
					{
						if ( r.message != "")
						{
							msg = msg + " " + r.message;
							Ext.Msg.alert('SSP Error', msg);							
						}
					}
				}
			}
		}

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