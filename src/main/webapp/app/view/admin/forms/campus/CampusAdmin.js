Ext.define('Ssp.view.admin.forms.campus.CampusAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.campusadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },
    height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){

    	Ext.apply(this,
    			{
    		      
    		      autoScroll: true,
     		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 50 },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 50
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: this.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-', {
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           }]  	
    	});

    	this.callParent(arguments);
    }
});