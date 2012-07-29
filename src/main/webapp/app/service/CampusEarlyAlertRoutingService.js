Ext.define('Ssp.service.CampusEarlyAlertRoutingService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	model: 'currentCampusEarlyAlertRouting',
    	store: 'campusEarlyAlertRoutingsStore'
    },
    config: {
    	personUrl: null
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( campusId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('campusEarlyAlertRouting') );
		baseUrl = baseUrl.replace("{id}",campusId);
		return baseUrl;
    },

    getCampusEarlyAlertRouting: function( campusId, id, callbacks ){
    	var me=this;
    	var url = me.getBaseUrl( campusId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	var model = new Ssp.model.reference.CampusEarlyAlertRouting();
	    	me.model.data = model.data;
	    	if (response.responseText != "")
	    	{
		    	r = Ext.decode(response.responseText);
		    	me.model.populateFromGenericObject(r);	    		
	    	}
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		// load
		me.apiProperties.makeRequest({
			url: url+'/'+id,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },   

    getAllCampusEarlyAlertRoutings: function( campusId, callbacks ){
    	var me=this;
    	var url = me.getBaseUrl( campusId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	if (response.responseText != "")
	    	{
		    	r = Ext.decode(response.responseText);
		    	me.store.loadData(r);	    		
	    	}
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		// load
		me.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },     
    
    saveCampusEarlyAlertRouting: function( campusId, jsonData, callbacks ){
    	var me=this;
    	var id=jsonData.id;
        var url = me.getBaseUrl( campusId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	// save
		if (id=="")
		{
			// create
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
    
    destroy: function( campusId, id, callbacks ){
    	var me=this;
        var url = me.getBaseUrl( campusId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, id, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
    	me.apiProperties.makeRequest({
   		   url: url+"/"+id,
   		   method: 'DELETE',
   		   successFunc: success,
   		   failureFunc: failure,
   		   scope: me
   	    }); 
    }
});