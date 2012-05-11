Ext.define('Ssp.controller.tool.actionplan.TaskTreeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	treeStore: 'treeStore',
    	treeUtils: 'treeRendererUtils'
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand'
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
	    		var nodes = me.treeUtils.createNodesFromJson(records, false);
		    	me.treeStore.setRootNode({
		    	        text: 'root',
		    	        expanded: true,
		    	        children: nodes
		    	});
	    	}else{
		    	me.treeStore.setRootNode({
	    	        text: 'root',
	    	        expanded: true,
	    	        children: []
	    	    });
	    	}		
		};
		
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl('reference/challengeCategory/'),
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
    }
    
	
});