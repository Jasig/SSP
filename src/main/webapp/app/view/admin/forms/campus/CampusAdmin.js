Ext.define('Ssp.view.admin.forms.campus.CampusAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.campusadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        model: 'currentCampus',
        store: 'campusesStore'
    },
    height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{   
    		      autoScroll: true,
    		      store: me.store,
     		      columns: [{
		    	        xtype:'actioncolumn',
		    	        width:65,
		    	        header: 'Action',
		    	        items: [{
			    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
			    	            tooltip: 'Edit Campus Early Alert Routings',
			    	            handler: function(grid, rowIndex, colIndex) {
			    	            	var rec = grid.getStore().getAt(rowIndex);
			    	            	var panel = grid.up('panel');
			    	                panel.model.data=rec.data;
			    	            	panel.appEventsController.getApplication().fireEvent('editCampusEarlyAlertRoutings');
			    	            },
			    	            scope: me
			    	        }]
     		              },{ header: 'Name',  
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
     		       		    store: me.store,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-', {
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_DELETE_BUTTON'),
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           }]  	
    	});

    	return me.callParent(arguments);
    }
});