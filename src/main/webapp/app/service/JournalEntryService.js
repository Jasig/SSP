Ext.define('Ssp.service.JournalEntryService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personJournalEntry') );
		baseUrl = baseUrl.replace('{id}', personId );
		return baseUrl;
    },

    getAll: function( personId, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl( personId ),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },
    
    save: function( personId, jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
		id = jsonData.id;

		// save
		if (id=="")
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
    			url: url+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}
    },
    
    destroy: function( personId, id, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, id, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	me.apiProperties.makeRequest({
   		   url: me.getBaseUrl( personId )+"/"+id,
   		   method: 'DELETE',
   		   successFunc: success,
   		   failureFunc: failure,
   		   scope: me
   	    }); 
    }
});