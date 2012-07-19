Ext.define('Ssp.service.CaseloadService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'caseloadStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );
    	return baseUrl;
    },

    getCaseload: function( programStatusId, callbacks ){
    	var me=this;
    	var programStatusFilter = "";
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.store.loadData( r.rows );
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
	    
	    if (programStatusId != "")
	    {
	    	programStatusFilter = '/?programStatusId='+programStatusId;
	    }
	    
	    me.store.removeAll();
	    
		me.apiProperties.makeRequest({
			url: me.getBaseUrl()+'/caseload'+programStatusFilter,
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },
    
    getCaseloadById: function( personId, callbacks ){
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
			url: me.getBaseUrl()+'/'+personId+'/caseload',
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }    
});