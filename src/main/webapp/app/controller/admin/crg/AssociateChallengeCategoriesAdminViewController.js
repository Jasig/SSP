Ext.define('Ssp.controller.admin.crg.AssociateChallengeCategoriesAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	store: 'challengeCategoriesStore',
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

    	this.treeUtils.getItems({url: 'reference/challengeCategory/', 
                                 nodeType: 'challengeCategory', 
                                 isLeaf: false});
    	
		return this.callParent(arguments);
    },
   
    onItemExpand: function( nodeInt, obj ){
    	var node = nodeInt;
    	var id = this.treeUtils.getIdFromNodeId(node.data.id);
    	this.treeUtils.getItems({url: 'reference/challenge/', 
                                 nodeType: 'challenge', 
                                 isLeaf: true, 
                                 nodeToAppendTo: node});   	   	
    },

    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
    	dropHandler.wait=true;
        
    	/*
    	console.log(data.records[0].get('name'));
        console.log(overModel);
        console.log(dropPosition);
        console.log(dropHandler);
        console.log(eOpts);
        */

    	// handle drop on a folder
        if (!overModel.isLeaf() && dropPosition == 'append')
        {
        	console.log('challengeId' + data.records[0].get('id'));
        	console.log('challengeCategoryId' + overModel.data.id);
        	console.log("Make a call to add the challenge to the category.");
        }

        // handle drop inside a folder
        if (dropPosition=='before' || dropPosition=='after')
        	console.log("You can't do that. Drop it on a folder instead.");
        
        dropHandler.cancelDrop;
        
        return 1;
    }
});