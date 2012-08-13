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
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personSearch') );
    	return baseUrl;
    },

    search: function( searchTerm, outsideCaseload, callbacks ){
    	var me=this;

	    me.store.removeAll();

		// Set params in the url for Search Store
		// because the params need to be applied prior to load and not in a params 
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous page
    	Ext.apply(me.store.getProxy(),{url: me.getBaseUrl()+'?sort=lastName&outsideCaseload='+outsideCaseload+'&searchTerm='+searchTerm});

	    me.store.load({
		    params: {
		        
		    },
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
    }
});