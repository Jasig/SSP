Ext.define('Ssp.view.admin.forms.campus.EarlyAlertRoutingsAdmin',{
	extend: 'Ext.grid.Panel',
	alias : 'widget.earlyalertroutingsadmin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EarlyAlertRoutingsAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        store: 'campusEarlyAlertRoutingsStore'
    },
    height: '100%',
	width: '100%',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{   
    		      autoScroll: true,
    		      store: me.store,
     		      columns: [
    		                { header: 'Group Name',  
    		                  dataIndex: 'name',
    		                  flex: 50 }
    		           ],
    		        
    		           dockedItems: [{
     		               xtype: 'toolbar',
      		               dock: 'top',
      		               items: [{
      		            	   xtype: "label",
     		                   text: "Routing Groups define optional endpoints where an Early Alert will be delivered."
     		               }]  
      		            },
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    store: me.store,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   tooltip: 'Add Early Alert Routing Group',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   tooltip: 'Edit Early Alert Routing Group',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   itemId: 'editButton'
     		               }, '-', {
     		                   text: 'Delete',
     		                   tooltip: 'Delete Early Alert Routing Group',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   itemId: 'deleteButton'
     		               }]
     		           }]  	
    	});

    	return me.callParent(arguments);
    }
});