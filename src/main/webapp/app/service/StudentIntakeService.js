Ext.define('Ssp.service.StudentIntakeService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('studentIntakeTool') );
    	return baseUrl;
    },
    
    get: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	// filter inactive items
    		r.rows = me.superclass.filterInactiveChildren( r.rows );	    	
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url+'/'+personId,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
    
    save: function( personId, jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl();
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		// save
		if (personId=="")
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url+"/"+personId,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}	
    }
});