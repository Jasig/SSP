Ext.define('Ssp.service.EarlyAlertService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'earlyAlertsStore',
    	treeStore: 'earlyAlertsTreeStore',
    	treeUtils: 'treeRendererUtils'
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
    			me.populateEarlyAlerts( r.rows );
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
    },

    getAllEarlyAlertResponses: function( personId, earlyAlertId, callbacks ){
    	var me=this;
    	var node = me.cleanResponses( earlyAlertId );
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
    		if (r.rows.length > 0)
	    	{
    			me.populateEarlyAlertResponses( node, r.rows );
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
			url: me.getBaseUrl(personId)+'/'+earlyAlertId+'/earlyAlertResponse',
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    },    
    
    populateEarlyAlerts: function( records ){
    	var me=this;
    	Ext.Array.each( records, function(record, index){
    		//record.iconCls='iconFolder';
    		record.leaf=false;
    		record.nodeType='early alert';
    	});
    	me.treeStore.setRootNode({
    	    text: 'EarlyAlerts',
    	    leaf: false,
    	    expanded: false
    	});

    	me.treeStore.getRootNode().appendChild(records);
    },
    
    populateEarlyAlertResponses: function( node, records ){
    	var me=this;
    	
    	Ext.Array.each( records, function(record, index){
    		//record.iconCls='iconFolder';
    		record.leaf=true;
    		record.nodeType='early alert response';
    	});

    	node.appendChild(records);
    },
    
    cleanResponses: function( earlyAlertId ){
    	var me=this;
    	node = me.treeStore.getNodeById( earlyAlertId );
    	node.removeAll();
    	return node;
    }
});