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
     		                   text: '',
     		                   tooltip: "Early Alert Routing Groups are used to route Early Alerts by the Referral Reason of the Early Alert. If you do not define a routing, the Early Alert Coordinator's email address will be used for Early Alert communication when no Counselor/Coach is assigned.",
     		                   cls: 'helpIcon',
	   			               width: 32,
						       height: 32,
     		                   xtype: 'button',
     		                   itemId: 'helpButton'
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