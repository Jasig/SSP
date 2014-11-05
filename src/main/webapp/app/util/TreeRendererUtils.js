/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.util.TreeRendererUtils',{	
	extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	treeStore: 'treeStore'
    },

	initComponent: function() {
		return this.callParent(arguments);
    },
	
	getTreeStore: function() {
		return this.treeStore;
	},
 
    /*
     * Find a child in the tree.
     * @id - the object id of the node to find
     */
    getNodeById: function( id ){
    	return this.treeStore.getNodeById( id );
    },    
    
    /*
     * Clears the treeStore instance, so new folders can be assigned.
     */
    clearRootCategories: function(){
    	// clear tree
    	this.treeStore.setRootNode({
	        text: 'root',
	        expanded: true,
	        children: []
	    });
    },
    
    /*
     * Appends children to the tree under a specified parentNode
     * @parentNode - the node at which to append
     * @children - the child nodes to append
     *    - Should be in the format of nodes as returned by the createNodes methods of this class. 
     */
    appendChildren: function(nodeToAppendTo, children) {
    	// Append to the root if no node is defined
    	if (nodeToAppendTo == null)
    	{
    		nodeToAppendTo = this.treeStore.getRootNode();
    	}else
    	{
    		// if not using the root
    		// then clean the node before append
    		nodeToAppendTo.removeAll();
    	}
    	
    	// only append if there are children
    	if (children.length > 0)
    	{
    		nodeToAppendTo.appendChild( children );
    	}
    },
    
    getNameFromNodeId: function( value ) {
    	var arr = value.split('_');
    	return arr[1];
    },
 
    getIdFromNodeId: function( value ) {
    	var arr = value.split('_');
    	return arr[0];
    },    
    
    /*
     * @records - Array of records in json format.
     * @isLeaf - Determines if the returned nodes array contains branch or leaf elements
     *             The default is to return branch elements.  
     */
    createNodes: function(records, isLeaf){
    	var nodes = [];
    	Ext.each(records, function(name, index) {
    		nodes.push({
    	    	        text: records[index].get('name'),
    	    	        id: records[index].get('id'),
    	    	        leaf: isLeaf || false
    	    	      });
    	});
    	return nodes;
    },
    
    /*
     * @records - Array of records in json format.
     * @isLeaf - Determines if the returned nodes array contains branch or leaf elements
     *             The default is to return branch elements.
     * @nodeType - An optional description for the node type. Used to identify the type of node for drag
     *             and drop functionality. For example with a nodeType set to 'challenge', the node will
     *             be created with an id such as 12345_challenge.
     * @expanded - whether or not a branch should load expanded   
     */
    createNodesFromJson: function(records, isLeaf, nodeType, enableCheckSelection, expanded, expandable, includeToolTip, toolTipFieldName, sortFunction){
	    var me = this;
    	var nodeIdentifier = "";
    	var enableCheckSelection = enableCheckSelection;
    	var nodes = [];
    	var nodeName = nodeType || "";
    	if (nodeName != "")
    	{
    		nodeIdentifier = '_' + nodeName;
    	}
		
		if(sortFunction && sortFunction != null)
    		records.sort(sortFunction);

    	Ext.each(records, function(name, index) {
    		var nodeData = {
        	        text: records[index].name + me.setObsoleteText(records[index]),
        	        id: records[index].id + nodeIdentifier,
        	        qtip: ((includeToolTip === true)? records[index][toolTipFieldName] : ""),
        	        leaf: isLeaf || false,
        	        expanded: expanded,
        	        expandable: expandable,
					qtitle: records[index].objectStatus
        	      };
        	
        	if (enableCheckSelection && isLeaf==true)
        		nodeData['checked']=false;
        	
    		nodes.push( nodeData );
    	});
		
    	return nodes;
    },   

   setObsoleteText: function(record){
       var txt = "";
		if(typeof record.extraObsoleteText !== "undefined"){
		  if(record.objectStatus !== 'ACTIVE' && record.extraObsoleteText.length == 0){
			  txt = ' (Inactive)';
		  }else{
		     txt += record.extraObsoleteText;
		  }
		}
		return txt;
	}, 
   
 
    /*
     * Retrieves items to populate the tree store.
     * @args.url - The url for the request to get items
	 * @args.nodeType - An optional name to append to the id to determine the name of node
	 * @args.isLeaf - Boolean, whether or not the items are branch or leaf nodes
	 * @args.nodeToAppendTo = the rootNode to append the items
	 * @args.enableCheckedItems = boolean to determine if a checkbox is created for leaf items in the tree
     * @args.expanded - boolean to determine whether a branch should appear as expanded
     * @args.expandable - boolean to determine whether or not the branch can be expanded or collapsed
     * @args.
     */
    getItems: function( treeRequest ){
    	var me=this;
    	var destroyBeforeAppend = treeRequest.get('destroyBeforeAppend');
    	var url = treeRequest.get('url');
    	var isLeaf = treeRequest.get('isLeaf');
    	var enableCheckSelection = treeRequest.get('enableCheckedItems');
    	var nodeToAppendTo = treeRequest.get('nodeToAppendTo');
    	var nodeType = treeRequest.get('nodeType');
    	var expanded = treeRequest.get('expanded');
    	var expandable = treeRequest.get('expandable');
		var responseFilter = treeRequest.get('responseFilter');
    	var callbackFunc = treeRequest.get('callbackFunc');
    	var callbackScope = treeRequest.get('callbackScope');
    	var removeParentWhenNoChildrenExist = treeRequest.get('removeParentWhenNoChildrenExist');
    	var includeToolTip = treeRequest.get('includeToolTip');
    	var toolTipFieldName = treeRequest.get('toolTipFieldName');
		var sortFunction = treeRequest.get('sortFunction');
    	var node = treeRequest.get('node');
		
    	// retrieve items
		me.apiProperties.makeRequest({
			url: me.apiProperties.createUrl( url ),
			method: 'GET',
			jsonData: '',
			successFunc: function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	var records = r.rows;
		    	var nodes = [];
		    	if (records.length > 0)
		    	{
					if ( responseFilter ) {
						records = responseFilter.apply(callbackScope, [records]);
					};
		    		nodes = me.createNodesFromJson(records, 
							isLeaf, 
							nodeType, 
							enableCheckSelection, 
							expanded, 
							expandable, 
							includeToolTip, 
							toolTipFieldName,
							sortFunction);
		    		me.appendChildren( nodeToAppendTo, nodes);
		    	}else{
		    		me.appendChildren( nodeToAppendTo, []);
		    		if (removeParentWhenNoChildrenExist==true)
		    		{
		    			nodeToAppendTo.remove(true);
		    		}
		    	}
		    	
	    		if (callbackFunc != null && callbackFunc != "")
	    			callbackFunc( callbackScope, node );
			}
		});
		return 1;
    },   
 
    /*
     * Retrieves items to populate the tree store.
     * @args.url - The url for the request to get items
	 * @args.nodeType - An optional name to append to the id to determine the name of node
	 * @args.isLeaf - Boolean, whether or not the items are branch or leaf nodes
	 * @args.nodeToAppendTo = the rootNode to append the items
	 * @args.enableCheckedItems = boolean to determine if a checkbox is created for leaf items in the tree
     * @args.expanded - boolean to determine whether a branch should appear as expanded
     * @args.expandable - boolean to determine whether or not the branch can be expanded or collapsed
     * @args.
     */
    getItemsWithParams: function( treeRequest,params ){
    	var me=this;
    	var destroyBeforeAppend = treeRequest.get('destroyBeforeAppend');
    	var url = treeRequest.get('url');
    	var isLeaf = treeRequest.get('isLeaf');
    	var enableCheckSelection = treeRequest.get('enableCheckedItems');
    	var nodeToAppendTo = treeRequest.get('nodeToAppendTo');
    	var nodeType = treeRequest.get('nodeType');
    	var expanded = treeRequest.get('expanded');
    	var expandable = treeRequest.get('expandable');
    	var callbackFunc = treeRequest.get('callbackFunc');
    	var callbackScope = treeRequest.get('callbackScope');
    	var removeParentWhenNoChildrenExist = treeRequest.get('removeParentWhenNoChildrenExist');
    	var includeToolTip = treeRequest.get('includeToolTip');
    	var toolTipFieldName = treeRequest.get('toolTipFieldName');
		var sortFunction = treeRequest.get('sortFunction');
		var node = treeRequest.get('node');
    	// retrieve items
		me.apiProperties.makeRequest({
			url: me.apiProperties.createUrl( url ),
			method: 'GET',
			jsonData: '',
			params: params,
			successFunc: function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	var records = r.rows;
				
		    	var nodes = [];
		    	if (records.length > 0)
		    	{
		    		nodes = me.createNodesFromJson(records, 
								isLeaf, 
								nodeType, 
								enableCheckSelection, 
								expanded, 
								expandable, 
								includeToolTip, 
								toolTipFieldName,
								sortFunction);
		    		
					me.appendChildren( nodeToAppendTo, nodes);
					
		    	}else{
		    		me.appendChildren( nodeToAppendTo, []);
		    		if (removeParentWhenNoChildrenExist==true)
		    		{
		    			nodeToAppendTo.remove(true);
		    		}
		    	}
		    	
	    		if (callbackFunc != null && callbackFunc != "")
	    			callbackFunc( callbackScope , node);
			}
		});
		return 1;
    }
});