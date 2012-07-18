Ext.define('Ssp.service.SearchService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'searchStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personSearch') );
    	return baseUrl;
    },

    search: function( searchTerm, outsideCaseload, callbacks ){
    	var me=this;
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.store.loadData(r.rows);
	    	}else{
	    		Ext.Msg.alert('Attention','No students match your search. Try a different search value.');
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
			url: me.getBaseUrl()+'/?outsideCaseload='+outsideCaseload+'&searchTerm='+searchTerm,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }
});