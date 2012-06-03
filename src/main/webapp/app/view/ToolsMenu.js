Ext.define('Ssp.view.ToolsMenu', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.toolsmenu',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.ToolsViewController',
    inject: {
        store: 'toolsStore'
    },
    initComponent: function(){
    	Ext.apply(this,
    			   {
		    		width: '100%',
		    		height: '100%',
    				store: this.store,

    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		    	    
    				columns:[{
    				           header: "Assigned Tools", 
    				           dataIndex: "name",
    				           sortable: true,
    				           flex:1 }]
		    	    });
    	
    	return this.callParent(arguments);
    }
});