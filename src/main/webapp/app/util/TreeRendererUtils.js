Ext.define('Ssp.util.TreeRendererUtils',{	
	extend: 'Ext.Component',

	initComponent: function() {
		return this.callParent(arguments);
    },

    createNodes: function(records){
    	var nodes = [];
    	Ext.each(records, function(name, index) {
    		nodes.push({
    	    	        text: records[index].get('name'),
    	    	        id: records[index].get('id'),
    	    	        leaf: false
    	    	      });
    	});
    	return nodes;
    },
    
    createNodesFromJson: function(records){
    	var nodes = [];
    	Ext.each(records, function(name, index) {
    		nodes.push({
    	    	        text: records[index].name,
    	    	        id: records[index].id,
    	    	        leaf: false
    	    	      });
    	});
    	return nodes;
    },   
    
});