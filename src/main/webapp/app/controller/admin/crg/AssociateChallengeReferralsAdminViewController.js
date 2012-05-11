Ext.define('Ssp.controller.admin.crg.AssociateChallengeReferralsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	store: 'challengesStore',
    	treeStore: 'treeStore',
    	treeUtils: 'treeRendererUtils'
    },

    control: {
    	view: {
    		itemexpand: 'onItemExpand'
    	},
    	
        treeView: {
            selector: '.treeview',
            listeners: {
                beforedrop: 'onBeforeDrop'
            }
        }  	
    },
    
	init: function() {
		var me = this;
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	var records = r.rows;
	    	if (records.length > 0)
	    	{
	    		var nodes = me.treeUtils.createNodesFromJson(records);
		    	me.treeStore.setRootNode({
		    	        text: 'root',
		    	        expanded: true,
		    	        children: nodes
		    	});
	    	}		
		};
		
		// clear the current items in the tree
    	me.treeStore.setRootNode({
	        text: 'root',
	        expanded: true,
	        children: []
	    });
		
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl('reference/challenge/'),
			method: 'GET',
			jsonData: '',
			successFunc: successFunc 
		});
    	
		return this.callParent(arguments);
    },
    
    onItemExpand: function(){
    	console.log('DisplayChallengeReferralsAdminViewController->onItemExpand');
    	// TODO: Retrieve related challenges and display as a subnode of the tree   	
    },

    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
    	dropHandler.wait=true;

    	// handle drop on a folder
        if (!overModel.isLeaf() && dropPosition == 'append')
        {
        	console.log('referralId' + data.records[0].get('id'));
        	console.log('challengeId' + overModel.data.id);
        	console.log("Make a call to add the referral to the challenge.");
        }

        // handle drop inside a folder
        if (dropPosition=='before' || dropPosition=='after')
        	console.log("You can't do that. Drop it on a folder");
        
        dropHandler.cancelDrop;
        
        return 1;
    }
});