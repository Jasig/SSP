Ext.define('Ssp.controller.admin.journal.AssociateTrackStepsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	store: 'journalTracksStore',
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

    	this.treeUtils.getItems({url: 'reference/journalTrack/', 
                                 nodeType: 'journalTrack', 
                                 isLeaf: false});
    	
		return this.callParent(arguments);
    },
   
    onItemExpand: function( nodeInt, obj ){
    	console.log('AssociateTrackStepsAdminViewController->onItemExpand');
    	var node = nodeInt;
    	var id = this.treeUtils.getIdFromNodeId(node.data.id);
    	this.treeUtils.getItems({url: 'reference/journalStep/', 
                                 nodeType: 'journalStep', 
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
        	console.log('stepId' + data.records[0].get('id'));
        	console.log('trackId' + overModel.data.id);
        	console.log("Make a call to add the step to the track.");
        }

        // handle drop inside a folder
        if (dropPosition=='before' || dropPosition=='after')
        	console.log("You can't do that. Drop it on a folder");
        
        dropHandler.cancelDrop;
        
        return 1;
    }	
});