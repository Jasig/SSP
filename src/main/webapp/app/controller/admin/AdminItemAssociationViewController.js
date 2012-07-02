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
    		itemexpand: 'onItemExpand',
    		itemcollapse: 'onItemCollapse'
    	},
    	
        treeView: {
            selector: '.treeview',
            listeners: {
                beforedrop: 'onBeforeDrop'
            }
        },
        
        'deleteAssociationButton': {
            click: 'deleteConfirmation'
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

    onItemCollapse: function( node, obj ){
    	var tree = this.getView();
    	var records = tree.getView().getChecked();
    	if (records.length > 0)
    	{
        	Ext.Array.each(records, function(rec){
    	        rec.data.checked=false;
    	    },this);    		
    	}
    },    
    
    clear: function(){
    	this.treeUtils.clearRootCategories();
    },
    
    getParentItems: function(){
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url', this.apiProperties.getItemUrl( this.getParentItemType() ) );
    	treeRequest.set('nodeType', this.getParentItemType() );
    	treeRequest.set('isLeaf', false);
    	this.treeUtils.getItems( treeRequest );
    },

    getAssociatedItems: function(node, id){
    	var parentUrl = this.apiProperties.getItemUrl( this.parentItemType );
    	var url = parentUrl + id + '/' + this.getAssociatedItemType() + '/';
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url',url);
    	treeRequest.set('nodeType', this.getAssociatedItemType);
    	treeRequest.set('isLeaf', true);
    	treeRequest.set('nodeToAppendTo', node);
    	treeRequest.set('enableCheckedItems', true);
    	this.treeUtils.getItems( treeRequest );
    },

    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
    	var me=this;
    	var url, parentId, associatedItemId, node;
    	
    	// ensure the drop handler waits for the drop
    	dropHandler.wait=true;

    	// handle drop on a folder
        if (!overModel.isLeaf() && dropPosition == 'append')
        {
        	node = overModel;
        	parentId = this.treeUtils.getIdFromNodeId(node.data.id);
        	associatedItemId = data.records[0].get('id')
        	parentUrl = this.apiProperties.getItemUrl( this.getParentItemType() ) + parentId + '/' + this.getAssociatedItemType() + '/'; 	
        	url = me.apiProperties.createUrl( parentUrl );
        	me.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: '"' + associatedItemId + '"',
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
 
    deleteConfirmation: function( button ) {
    	var me=this;
    	var tree = me.getView();
    	var treeUtils = me.treeUtils;
    	var records = tree.getView().getChecked();
    	if (records.length > 0)
    	{
     	   message = 'You are about to delete the selected item associations. Would you like to continue?';
     	      	   
            Ext.Msg.confirm({
    		     title:'Delete?',
    		     msg: message,
    		     buttons: Ext.Msg.YESNO,
    		     fn: me.onDeleteAssociationConfirm,
    		     scope: me
    		   });
    	}else{
    		Ext.Msg.alert('Error', 'Please select an item to delete.');
    	}
    },    
    
    onDeleteAssociationConfirm: function( btnId){
    	var tree = this.getView();
    	var treeUtils = this.treeUtils;
    	var records = tree.getView().getChecked();
    	var parentId, parentNode;
     	if (btnId=="yes")
     	{
        	parentId = treeUtils.getIdFromNodeId( records[0].data.parentId );
        	// To set the parentNode use the full parentId, while the previously set parentId attribute
        	// used the treeUtils.getIdFromNodeId() method to trim the id of the category description
        	// so the id from the database could be ascertained. The actual parentId attribute in the data
        	// from the record is separated by a character like this: 'guid_category'
        	parentNode = tree.getView().getStore().findRecord('id', records[0].data.parentId ); 

        	Ext.Array.each(records, function(rec){
    	        var associatedItemId = treeUtils.getIdFromNodeId( rec.data.id );
    	        this.deleteAssociation( parentId, associatedItemId, parentNode );
    	    },this);   		
    	}
	},
	
    deleteAssociation: function( parentId, associatedItemId, node ){
    	var me=this;
    	var url, parentId, associatedItemId;
    	if ( parentId != "" && associatedItemId != null)
    	{
        	parentUrl = this.apiProperties.getItemUrl( this.getParentItemType() ) + parentId + '/' + this.getAssociatedItemType() + '/'; 	
	    	url = me.apiProperties.createUrl( parentUrl );
	    	me.apiProperties.makeRequest({
				url: url,
				method: 'DELETE',
				jsonData: '"' + associatedItemId + '"',
				successFunc: successFunc = function(response, view) {
					me.getAssociatedItems(node, parentId);
				} 
			});
    	}
    }
});