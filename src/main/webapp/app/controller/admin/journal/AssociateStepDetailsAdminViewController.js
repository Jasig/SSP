Ext.define('Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	store: 'journalStepsStore',
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

    	this.treeUtils.getItems({url: 'reference/journalStep/', 
                                 nodeType: 'journalStep', 
                                 isLeaf: false});
    	
		return this.callParent(arguments);
    },
    
    onItemExpand: function(){
    	console.log('AssociateStepDetailsAdminViewController->onItemExpand');
    	var node = nodeInt;
    	var id = this.treeUtils.getIdFromNodeId(node.data.id);
    	this.treeUtils.getItems({url: 'reference/journalDetail/', 
                                 nodeType: 'journalDetail', 
                                 isLeaf: true, 
                                 nodeToAppendTo: node});      	
    },

    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
    	dropHandler.wait=true;

    	// handle drop on a folder
        if (!overModel.isLeaf() && dropPosition == 'append')
        {
        	console.log('detailId' + data.records[0].get('id'));
        	console.log('stepId' + overModel.data.id);
        	console.log("Make a call to add the details to the step.");
        }

        // handle drop inside a folder
        if (dropPosition=='before' || dropPosition=='after')
        	console.log("You can't do that. Drop it on a folder");
        
        dropHandler.cancelDrop;
        
        return 1;
    }

});