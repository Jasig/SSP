/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
		return 20;
	},

	getInfinitePagingSize: function(){
		return -1;
	},
	
	getProxy: function(url){
		var proxyObj = {
			type: 'rest',
			url: this.createUrl(url),
			simpleSortMode: true,
			directionParam:'sortDirection',
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
		var paramString= "";
		if (args.params != undefined && args.params != null)
		{
			 for(var index in args.params){
		        var param = args.params[index];
		        paramString = paramString + index + "=" + param + "&";
			 }
			
			if(paramString != undefined && paramString.length > 0){
				var paramString  = "?" + paramString.substring(0, paramString.length - 1);
			}
		}
		
		Ext.Ajax.request({
			url: args.url + paramString,
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
		if (response.status==404 || response.status==405 || response.status==400)
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
		var index = this.apiUrlStore.findExact('name', itemName);
		if(index == undefined || index < 0 )
			return "";
		var record = this.apiUrlStore.getAt(index);
		var url = "";
		if (record != null)
			url = record.get('url');
		return url;
	},
	
	getReporter: function(){
		return Ext.ComponentQuery.query('sspreport')[0];
	}
});