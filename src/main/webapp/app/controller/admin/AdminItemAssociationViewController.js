Ext.define('Ssp.controller.admin.AdminItemAssociationViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	treeUtils: 'treeRendererUtils'
    },

    config: {
        associatedItemType: "",
        parentItemType: "",
        parentIdAttribute: "",
        associatedItemIdAttribute: ""
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
    
	constructor: function( config ) {
		this.initConfig(config);
		return this.callParent(arguments);
    },
   
    onItemExpand: function( nodeInt, obj ){
    	var node = nodeInt;
    	var id = this.treeUtils.getIdFromNodeId(node.data.id);
    	this.getAssociatedItems(node,id);
    },
    
    clear: function(){
    	this.treeUtils.clearRootCategories();
    },
    
    getParentItems: function(){
    	this.treeUtils.getItems({url: this.apiProperties.getItemUrl( this.getParentItemType() ), 
            nodeType: this.getParentItemType(), 
            isLeaf: false});
    },

    getAssociatedItems: function(node, id){
    	// TODO: Use the id prop to pull only the items associated with
    	// the expanded node
    	this.treeUtils.getItems({url: this.apiProperties.getItemUrl( this.getAssociatedItemType() ), 
            nodeType: this.getAssociatedItemType(), 
            isLeaf: true, 
            nodeToAppendTo: node});
    },

    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
    	var me=this;
    	var jsonData, url, parentId, associatedItemId, node;
    	
    	// ensure the drop handler waits for the drop
    	dropHandler.wait=true;

    	// handle drop on a folder
        if (!overModel.isLeaf() && dropPosition == 'append')
        {
        	//console.log('challengeId' + data.records[0].get('id'));
        	//console.log('challengeCategoryId' + overModel.data.id);
        	//console.log("Make a call to add the challenge to the category.");
        	node = overModel;
        	parentId = this.treeUtils.getIdFromNodeId(node.data.id);
        	associatedItemId = data.records[0].get('id')
        	jsonData = new Object();
        	jsonData[me.getParentIdAttribute()] = parentId;
        	jsonData[me.getAssociatedItemIdAttribute()] = associatedItemId;
        	parentUrl = this.apiProperties.getItemUrl( this.getParentItemType() ) + parentId +'/' + this.getAssociatedItemType(); 	
        	url = me.apiProperties.createUrl( parentUrl );
        	console.log(jsonData);
        	console.log(url);
        	console.log

			me.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: function(response, view) {
					me.getAssociatedItems(node, parentId);
				}
			});
        }

        // handle drop inside a folder
        if (dropPosition=='before' || dropPosition=='after')
        	console.log("You can't do that. Drop it on a folder instead.");
        
        dropHandler.cancelDrop;
        
        return 1;
    },
    
    deleteAssociation: function(){
    	var me=this;
    	var jsonData, url, parentId, associatedItemId, node;
    	
    	/*
    	 * TODO: If you can perform the delete then call the delete method to remove
    	 * the association.
    	 */
    	
    	/*
    	node = overModel;
    	parentId = this.treeUtils.getIdFromNodeId(node.data.id);
    	associatedItemId = data.records[0].get('id')
    	jsonData = new Object();
    	jsonData[me.getParentIdAttribute()] = parentId;
    	jsonData[me.getAssociatedItemIdAttribute()] = associatedItemId;
    	parentUrl = this.apiProperties.getItemUrl( this.getParentItemType() ) + parentId +'/' + this.getAssociatedItemType();
    	url = me.apiProperties.createUrl( parentUrl );

    	me.apiProperties.makeRequest({
			url: ,
			method: 'DELETE',
			jsonData: jsonData,
			successFunc: successFunc = function(response, view) {
				me.getAssociatedItems(node, parentId);
			}; 
		});
		*/
    }
});