Ext.define('Ssp.view.ToolsMenu', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.toolsmenu',
	id: 'ToolsMenu',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        toolsStore: 'toolsStore'
    },
	width: '100%',
	height: '100%',
	
    initComponent: function(){
    	Ext.apply(this,
    			   {
    				store: this.toolsStore,
    				columns:[{
    				           header: "Assigned Tools", 
    				           dataIndex: "name", 
    				           flex:1 }]
		    	    });
    	
    	this.callParent(arguments);
    }
});