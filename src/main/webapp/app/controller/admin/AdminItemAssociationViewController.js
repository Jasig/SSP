/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
        associatedItemIdAttribute: "",
		nodeSortFunction: ""
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
		var me=this;
		me.initConfig(config);
		return me.callParent(arguments);
    },
   
    onItemExpand: function( nodeInt, obj ){
    	var me=this;
    	var node = nodeInt;
		
    	var id = me.treeUtils.getIdFromNodeId(node.data.id);
		
    	me.getAssociatedItems(node,id);
    },

    onItemCollapse: function( node, obj ){
    	var me=this;
    	var tree = me.getView();
    	var records = tree.getView().getChecked();
    	if (records.length > 0)
    	{
        	Ext.Array.each(records, function(rec){
    	        rec.data.checked=false;
    	    },me);    		
    	}
    },    
    
    clear: function(){
    	this.treeUtils.clearRootCategories();
    },
    
    getParentItems: function(){
    	var me=this;
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url', me.apiProperties.getItemUrl( me.getParentItemType() ) );
    	treeRequest.set('nodeType', me.getParentItemType() );
    	treeRequest.set('isLeaf', false);
    	treeRequest.set('callbackFunc', me.onLoadComplete);
    	treeRequest.set('callbackScope', me);
    	me.treeUtils.getItemsWithParams( treeRequest , {limit: '-1'});
		
    }, 
    
    getParentItemsWithParams: function(params){
    	var me=this;
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url', me.apiProperties.getItemUrl( me.getParentItemType() ) );
    	treeRequest.set('nodeType', me.getParentItemType() );
    	treeRequest.set('isLeaf', false);
    	treeRequest.set('callbackFunc', me.onLoadCompleteExpand);
    	treeRequest.set('callbackScope', me);
    	me.treeUtils.getItemsWithParams( treeRequest, params );
		
    }, 
    
    getAssociatedItems: function(node, id){
    	var me=this;
    	var parentUrl = me.apiProperties.getItemUrl( me.parentItemType );
    	var url = parentUrl + '/' + id + '/' + me.getAssociatedItemType();
    	var treeRequest = new Ssp.model.util.TreeRequest();
    	treeRequest.set('url',url);
    	treeRequest.set('nodeType', me.getAssociatedItemType);
    	treeRequest.set('isLeaf', true);
    	treeRequest.set('nodeToAppendTo', node);
    	treeRequest.set('enableCheckedItems', true);
    	treeRequest.set('callbackFunc', me.onLoadComplete);
    	treeRequest.set('callbackScope', me);
		treeRequest.set('node', node);
		treeRequest.set('sortFunction', me.nodeSortFunction);
    	me.treeUtils.getItemsWithParams( treeRequest , {limit: '-1', status: 'ACTIVE'});
		
    },

	onLoadComplete: function( scope, node ){
		var me=scope;
		if (node && node != ""){
			if(node.get('qtitle') == 'INACTIVE' && !node.hasChildNodes()) {
				node.remove(true);
			}
		}
	},
	
	onLoadCompleteExpand: function( scope ){
		var me=scope;
		
		/*scope.getView().getView().getTreeStore().getRootNode().expandChildren(true, function(scope){
			console.log('done');
			console.log(scope);
		}, me);*/
		scope.getView().getView().getTreeStore().getRootNode().expandChildren();
		
	},
	
	
    
    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
    	var me=this;
    	var url, parentId, associatedItemId, node;
    	
    	// ensure the drop handler waits for the drop
    	dropHandler.wait=true;
    	
    	if(data.records[0].get('objectStatus') ==='INACTIVE')
    	{
            Ext.Msg.alert('SSP Error','You cannot assign inactive reference items');
            dropHandler.cancelDrop;
            return 1;
    	}
		
    	// handle drop on a folder
        if (!overModel.isLeaf() && dropPosition == 'append')
        {
        	node = overModel;
        	parentId = me.treeUtils.getIdFromNodeId(node.data.id);
			
        	associatedItemId = data.records[0].get('id');
        	parentUrl = me.apiProperties.getItemUrl( me.getParentItemType() ) + '/' + parentId + '/' + me.getAssociatedItemType(); 	
        	url = me.apiProperties.createUrl( parentUrl );
		
			if (node.get('qtitle') && node.get('qtitle') == 'INACTIVE') {
				dropHandler.cancelDrop;
        		return 1;
			}
			
			me.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: '"' + associatedItemId + '"',
				successFunc: function(response, view){
					me.getAssociatedItems(node, parentId);
				}
			});
        }

        // handle drop inside a folder
        if (dropPosition=='before' || dropPosition=='after')
        {
        	// provide a message or instruction if you'd like
        }	
        
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
    	var me=this;
    	var tree = me.getView();
    	var treeUtils = me.treeUtils;
    	var records = tree.getView().getChecked();
    	var parentId, parentNode;
     	if (btnId=="yes")
     	{
        	parentId = treeUtils.getIdFromNodeId( records[0].data.parentId );
        	// To set the parentNode use the full parentId, while the previously set parentId attribute
        	// used the treeUtils.getIdFromNodeId() method to trim the id of the category description
        	// so the id from the database could be ascertained. The actual parentId attribute in the data
        	// from the record is separated by a character like this: 'guid_category'
        	parentNode = tree.getView().getStore().findRecord('id', records[0].data.parentId, 0, false, false, true ); 

        	Ext.Array.each(records, function(rec){
    	        var associatedItemId = treeUtils.getIdFromNodeId( rec.data.id );
    	        this.deleteAssociation( parentId, associatedItemId, parentNode );
    	    },me);   		
    	}
	},
	
    deleteAssociation: function( parentId, associatedItemId, node ){
    	var me=this;
    	var url, parentId, associatedItemId;
    	if ( parentId != "" && associatedItemId != null)
    	{
        	parentUrl = me.apiProperties.getItemUrl( me.getParentItemType() ) + '/' + parentId + '/' + me.getAssociatedItemType(); 	
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