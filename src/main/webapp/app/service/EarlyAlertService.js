Ext.define('Ssp.service.EarlyAlertService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'earlyAlertsStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personEarlyAlert') );
		baseUrl = baseUrl.replace( '{personId}', personId );
		return baseUrl;
    },

    getAll: function( personId, callbacks ){
    	var me=this;
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
    		if (r.rows.length > 0)
	    	{
    			me.store.loadData(r.rows);
	    	}
	    	if (callbacks != null)
	    	{
	    		callbacks.success( r, callbacks.scope );
	    	}	
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	if (callbacks != null)
	    	{
	    		callbacks.failure( response, callbacks.scope );
	    	}
	    };
	    
	    me.store.removeAll();
	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl(personId),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }  
});