Ext.define('Ssp.view.tools.earlyalert.EarlyAlert', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.earlyalert',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentEarlyAlert',
        store: 'earlyAlertsStore'
    },
	width: '100%',
	height: '100%',
	
	initComponent: function() {	
    	var me=this;
		var sm = Ext.create('Ext.selection.CheckboxModel');

		Ext.apply(me, 
				{
		            autoScroll: true,
		            title: 'Early Alerts',

	    		      columns: [{
					    	        xtype:'actioncolumn',
					    	        width:65,
					    	        header: 'Action',
					    	        items: [{
					    	            icon: Ssp.util.Constants.GRID_ITEM_MAIL_REPLY_ICON_PATH,
					    	            tooltip: 'Respond to this Early Alert',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	                panel.appEventsController.getApplication().fireEvent('respondToEarlyAlert');
					    	            },
					    	            scope: me
					    	        }]
				                },
	    		                { header: 'Created By',  
	    		                  dataIndex: 'createdBy',
	    		                  renderer: me.columnRendererUtils.renderCreatedBy,
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 },
	    		                { header: 'Created Date',  
	    		                  dataIndex: 'createdBy',
	    		                  renderer: me.columnRendererUtils.renderCreatedByDateWithTime,
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 },
	    		                { header: 'Course',
	    		                  dataIndex: 'courseName',
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 }
	    		                 ] ,
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [/*{
				            tooltip: 'Respond to the selected Early Alert',
				            text: 'Respond',
				            xtype: 'button',
				            itemId: 'respondButton'
				        }*/{
				            tooltip: 'Display Tree',
				            text: 'Tree',
				            xtype: 'button',
				            itemId: 'displayTreeButton'
				        }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});