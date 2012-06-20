Ext.define('Ssp.view.ToolsMenu', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.toolsmenu',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.ToolsViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
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
    				           header: "Tools", 
    				           dataIndex: "name",
    				           sortable: false,
    				           menuDisabled: true,
    				           flex:1 },{
	    			    	        xtype:'actioncolumn',
	    			    	        width:18,
	    			    	        header: '',
	    			    	        items: [{
	    			    	            tooltip: 'Add Tool',
	    			    	            // icon: Ssp.util.Constants.ADD_TOOL_ICON_PATH,
	    			    	            getClass: this.columnRendererUtils.renderAddToolIcon,
	    			    	            handler: function(grid, rowIndex, colIndex) {
	    			    	            	var rec = grid.getStore().getAt(rowIndex);
	    			    	            	var panel = grid.up('panel');
	    			    	                //panel.toolId.data=rec.data.toolId;
	    			    	                panel.appEventsController.getApplication().fireEvent('addTool');
	    			    	                Ext.Msg.alert('Attention','This feature is not yet active');
	    			    	            },
	    			    	            scope: this
	    			    	        }]
	    		                }]
		    	    });
    	
    	return this.callParent(arguments);
    }
});