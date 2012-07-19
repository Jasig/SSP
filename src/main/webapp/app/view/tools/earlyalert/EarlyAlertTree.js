Ext.define('Ssp.view.tools.earlyalert.EarlyAlertTree', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.earlyalerttree',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertTreeViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentEarlyAlert',
        treeStore: 'earlyAlertsTreeStore'
    },
	width: '100%',
	height: '100%',
	
	initComponent: function() {	
    	var me=this;
		Ext.apply(me, 
				{
		            autoScroll: true,
		            title: 'Early Alerts',
		            collapsible: false,
		            useArrows: true,
		            rootVisible: false,
		            store: me.treeStore,
		            multiSelect: false,
		            singleExpand: true,
    		        columns: [{
    		            xtype: 'treecolumn',
    		            text: 'Responses',
    		            flex: .5,
    		            sortable: false,
    		            dataIndex: 'text'
    		        },{
    		            text: 'Created By',
    		            flex: 1,
    		            dataIndex: 'createdBy',
    		            renderer : me.columnRendererUtils.renderCreatedBy,
    		            sortable: false
    		        },{
    		            text: 'Created Date',
    		            flex: 1,
    		            dataIndex: 'createdDate',
    		            renderer : me.columnRendererUtils.renderCreatedByDateWithTime,
    		            sortable: false
    		        },{
    		            text: 'Course',
    		            flex: 2,
    		            sortable: false,
    		            dataIndex: 'courseName'
    		        }],
    		        
    		        dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Respond to the selected Early Alert',
				            text: '',
				            xtype: 'button',
			            	width: 28,
					        height: 28,
				            cls: 'earlyAlertResponseIcon',
				            itemId: 'respondButton'
				        }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});