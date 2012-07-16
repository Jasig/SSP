Ext.define('Ssp.service.PersonService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    config: {
    	personUrl: null
    },
    initComponent: function() {
    	var me=this;
    	
		me.personUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );

		return me.callParent( arguments );
    },

    getPerson: function( id, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		// load the person to edit
		me.apiProperties.makeRequest({
			url: me.personUrl+'/'+id,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },    
    
    savePerson: function( jsonData, callbacks ){
    	var me=this;
    	var id=jsonData.id;
        
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
        
    	// save the person
		if (id=="")
		{				
			// create
			me.apiProperties.makeRequest({
    			url: me.personUrl,
    			method: 'POST',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: me.personUrl+"/"+id,
    			method: 'PUT',
    			jsonData: jsonData,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}   	
    }
});