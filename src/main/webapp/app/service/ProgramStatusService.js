Ext.define('Ssp.service.ProgramStatusService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'programStatusesStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('programStatus') );
    	return baseUrl;
    },

    getAll: function( callbacks ){
    	var me=this;
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
    		if (r.rows.length > 0)
	    	{
    		    me.store.removeAll();
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
	    	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl(),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }
});