Ext.define('Ssp.service.EarlyAlertResponseService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId, earlyAlertId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personEarlyAlertResponse') );
		baseUrl = baseUrl.replace( '{personId}', personId );
		baseUrl = baseUrl.replace( '{earlyAlertId}', earlyAlertId );
		return baseUrl;
    },

    save: function( personId, earlyAlertId, jsonData, callbacks ){
    	var me=this;
    	var id = jsonData.id;
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
    		console.log( response );
    		if (r.id.length > 0)
	    	{
		    	if (callbacks != null)
		    	{
		    		callbacks.success( r, callbacks.scope );
		    	}
	    	}
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	if (callbacks != null)
	    	{
	    		callbacks.failure( response, callbacks.scope );
	    	}
	    };
	    
		if ( id.length > 0 )
		{
			// editing
			this.apiProperties.makeRequest({
				url: me.getBaseUrl( personId, earlyAlertId )+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: success,
				failureFunc: failure,
				scope: me
			});
			
		}else{
			// adding
			this.apiProperties.makeRequest({
				url: me.getBaseUrl( personId, earlyAlertId ),
				method: 'POST',
				jsonData: jsonData,
				successFunc: success,
				failureFunc: failure,
				scope: me
			});		
		}
    }  
});