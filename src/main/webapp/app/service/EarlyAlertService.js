Ext.define('Ssp.service.EarlyAlertService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	earlyAlertOutcomesStore: 'earlyAlertOutcomesStore',
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
    	var url = "";
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
	    
	    url = me.getBaseUrl(personId);
	    if (earlyAlertId != null && earlyAlertId != "")
	    {
			me.apiProperties.makeRequest({
				url: url + '/' + earlyAlertId + '/response',
				method: 'GET',
				successFunc: success,
				failureFunc: failure,
				scope: me
			});	    	
	    }
    },    
    
    populateEarlyAlerts: function( records ){
    	var me=this;

    	Ext.Array.each( records, function(record, index){
    		//record.iconCls='earlyAlertTreeIcon';
    		record.leaf=false;
    		record.nodeType='early alert';
    		record.gridDisplayDetails=record.courseName + " - " + record.courseTitle;
    		record.expanded=false;
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
    		//record.iconCls='earlyAlertTreeIcon';
    		record.leaf=true;
    		record.nodeType='early alert response';
    		record.gridDisplayDetails=me.earlyAlertOutcomesStore.getById(record.earlyAlertOutcomeId).get('name');
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