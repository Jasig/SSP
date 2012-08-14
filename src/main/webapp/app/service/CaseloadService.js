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
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personCaseload') );
    	return baseUrl;
    },

    getCaseload: function( programStatusId, callbacks ){
    	var me=this;
	    
		// clear the store
		me.store.removeAll();

		// Set the Url for the Caseload Store
		// including param definitions because the params need
		// to be applied prior to load and not in a params 
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous
		// page
    	Ext.apply(me.store.getProxy(),{url: me.getBaseUrl()+'?sort=lastName&programStatusId='+programStatusId});

	    me.store.load({
		    callback: function(records, operation, success) {
		        if (success)
		        {
			    	if (callbacks != null)
			    	{
			    		callbacks.success( records, callbacks.scope );
			    	}		        	
		        }else{
			    	if (callbacks != null)
			    	{
			    		callbacks.failure( records, callbacks.scope );
			    	}
		        }
		    },
		    scope: me
		});
    },
    
    getCaseloadById: function( personId, callbacks ){
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
			url: me.getBaseUrl()+'/'+personId+'/caseload',
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }    
});