Ext.define('Ssp.controller.admin.crg.AssociateChallengeReferralsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	store: 'challengesStore',
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
		this.treeUtils.clearRootCategories();

    	this.treeUtils.getItems({url: 'reference/challenge/', 
                                 nodeType: 'challenge', 
                                 isLeaf: false});
    	
		return this.callParent(arguments);
    },
    
    onItemExpand: function( nodeInt, obj ){
    	var node = nodeInt;
    	var id = this.treeUtils.getIdFromNodeId(node.data.id);
    	this.treeUtils.getItems({url: 'reference/challengeReferral/', 
                                 nodeType: 'referral', 
                                 isLeaf: true, 
                                 nodeToAppendTo: node});  	
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