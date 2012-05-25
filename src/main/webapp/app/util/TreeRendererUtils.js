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
    	
    	nodeToAppendTo.appendChild( children );
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
     */
    createNodesFromJson: function(records, isLeaf, nodeType, enableCheckSelection){
    	var nodeIdentifier = "";
    	var enableCheckSelection = enableCheckSelection;
    	var nodes = [];
    	var nodeName = nodeType || "";
    	if (nodeName != "")
    		nodeIdentifier = '_' + nodeName;
    	
    	Ext.each(records, function(name, index) {
    		var nodeData = {
        	        text: records[index].name,
        	        id: records[index].id + nodeIdentifier,
        	        leaf: isLeaf || false
        	      };
        	
        	if (enableCheckSelection && isLeaf==true)
        		nodeData['checked']=false;
        	
    		nodes.push( nodeData );
    	});
    	
    	return nodes;
    },   
 
    /*
     * Retrieves items to populate the tree store.
     * @args.url - The url for the request to get items
	 * @args.nodeType - An optional name to append to the id to determine the name of node
	 * @args.isLeaf - Boolean, whether or not the items are branch or leaf nodes
	 * @args.nodeToAppendTo = the rootNode to append the items
	 * @args.enableCheckedItems = boolean to determine if a checkbox is created for leaf items in the tree
     */
    getItems: function( treeRequest ){
    	var me=this;
    	var destroyBeforeAppend = treeRequest.get('destroyBeforeAppend');
    	var url = treeRequest.get('url');
    	var isLeaf = treeRequest.get('isLeaf');
    	var enableCheckSelection = treeRequest.get('enableCheckedItems');
    	var nodeToAppendTo = treeRequest.get('nodeToAppendTo');
    	var nodeType = treeRequest.get('nodeType');
    	// retrieve items
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl( url ),
			method: 'GET',
			jsonData: '',
			successFunc: function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	var records = r.rows;
		    	var nodes = [];
		    	if (records.length > 0)
		    	{
		    		nodes = me.createNodesFromJson(records, isLeaf, nodeType, enableCheckSelection);
		    		me.appendChildren( nodeToAppendTo, nodes);
		    	}
			}
		});
    },    
    
});