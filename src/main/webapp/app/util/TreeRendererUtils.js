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
     */
    createNodesFromJson: function(records, isLeaf, nodeType){
    	var nodeIdentifier = "";
    	var nodes = [];
    	var nodeName = nodeType || "";
    	if (nodeName != "")
    		nodeIdentifier = '_' + nodeName;
    	Ext.each(records, function(name, index) {
    		nodes.push({
    	    	        text: records[index].name,
    	    	        id: records[index].id + nodeIdentifier,
    	    	        leaf: isLeaf || false
    	    	      });
    	});
    	return nodes;
    },   
 
    /*
     * Retrieves items to populate the tree store.
     * @args.url - The url for the request to get items
	 * @args.nodeType - An optional name to append to the id to determine the name of node
	 * @args.isLeaf - Boolean, whether or not the items are branch or leaf nodes
	 * @args.nodeToAppendTo = the rootNode to append the items
     */
    getItems: function( args ){
    	var me=this;
    	var destroyBeforeAppend = false;
    	if (args.destroyBeforeAppend)
    		destroyBeforeAppend = true;
    	// retrieve categories
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl(args.url),
			method: 'GET',
			jsonData: '',
			successFunc: function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	var records = r.rows;
		    	var nodes = [];
		    	if (records.length > 0)
		    	{
		    		nodes = me.createNodesFromJson(records, args.isLeaf, args.nodeType);
		    		me.appendChildren( args.nodeToAppendTo, nodes);
		    	}
			}
		});
    },    
    
});