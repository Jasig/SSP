Ext.define('Ssp.view.tools.EarlyAlert', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.earlyalert',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertToolViewController',
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
        store: 'earlyAlertsStore'
    },
	width: '100%',
	height: '100%',
	
	initComponent: function() {	
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(this, 
				{
		            autoScroll: true,
		            title: 'Early Alerts',

	    		      columns: [
	    		                { header: 'Name',  
	    		                  dataIndex: 'name',
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 },
	    		                { header: 'Description',
	    		                  dataIndex: 'description', 
	    		                  flex: 50,
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 }
	    		           ],
				    
				    dockedItems: [{
				        dock: 'bottom',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Print the History for this student',
				            text: 'View History',
				            xtype: 'button',
				            itemId: 'viewHistoryButton'
				        }]
				    },{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Respond to the selected Early Alert',
				            text: 'Respond',
				            xtype: 'button',
				            itemId: 'respondButton'
				        }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});