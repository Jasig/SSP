Ext.define('Ssp.controller.tool.actionplan.TaskTreeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
    	treeStore: 'treeStore',
    	treeUtils: 'treeRendererUtils'
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand',
    		itemClick: 'onItemClick'
    	},
   	
    	'searchButton': {
			click: 'onSearchClick'
		}   	
    },
    
	init: function() {
		var me = this;
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	var records = r.rows;
	    	if (records.length > 0)
	    	{
	    		var nodes = me.treeUtils.createNodesFromJson(records, true);
		    	me.treeStore.setRootNode({
		    	        text: 'root',
		    	        expanded: true,
		    	        children: nodes
		    	});
	    	}		
		};
		
    	me.treeStore.setRootNode({
	        text: 'root',
	        expanded: true,
	        children: []
	    });
		
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl('reference/challengeReferral/'),
			method: 'GET',
			jsonData: '',
			successFunc: successFunc 
		});
    	
		return this.callParent(arguments);
    },
   
    onItemExpand: function(){
    	console.log('TaskTreeViewController->onItemExpand');
    	// TODO: Retrieve related challenges and display as a subnode of the tree   	
    	// TODO: Retrieve related referrals and display as a subnode of the tree  	
    },
    
    onSearchClick: function(){
    	console.log('TaskTreeViewController->onSearchClick');    	
    },
    
    onItemClick: function(view, record, item, index, e, eOpts){
    	var me=this;
    	console.log('TaskTreeViewController->onItemClick');    	
    	console.log(record);
    	var id = record.data.id;
    	// assuming it's a referral
    	// load the referral
    	var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	if (r)
	    	{
	    		var args = new Object();
	    		args.name = r.name;
	    		args.description = r.description || '';
	    		args.challengeReferralId = r.challengeReferralId;
	    		me.appEventsController.getApplication().fireEvent('loadTask', args);
	    	}		
		};
    	
    	this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl('reference/challengeReferral/')+id,
			method: 'GET',
			jsonData: '',
			successFunc: successFunc 
		});
    }
    
	
});