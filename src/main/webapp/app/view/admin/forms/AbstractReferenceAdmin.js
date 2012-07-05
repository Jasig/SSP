Ext.define('Ssp.view.admin.forms.AbstractReferenceAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.abstractreferenceadmin',
	title: 'Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.AbstractReferenceAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },
	height: '100%',
	width: '100%',
	autoScroll: true,

    initComponent: function(){
    	var cellEditor = Ext.create('Ext.grid.plugin.RowEditing',
		                             { clicksToEdit: 2 });
    	Ext.apply(this,
    			{
    		      plugins:cellEditor,
    		      selType: 'rowmodel',
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 50 
    		                 },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 50,
    		                  field: {
    		                      xtype: 'textfield'
    		                  }
    		                },
    		                { 
    		                  header: 'Status',
      		                  dataIndex: 'objectStatus' 
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
    		               dock: 'top',
    		               items: [{
    		                   text: 'Add',
    		                   iconCls: 'icon-add',
    		                   xtype: 'button',
    		                   action: 'add',
    		                   itemId: 'addButton'
    		               }, '-', {
    		                   text: 'Delete',
    		                   iconCls: 'icon-delete',
    		                   xtype: 'button',
    		                   action: 'delete',
    		                   itemId: 'deleteButton'
    		               }]
    		           },{
    		               xtype: 'toolbar',
    		               dock: 'top',
    		               items: [{
    	                      xtype: 'label',
    	                       text: 'Double-click to edit an item.'
    	                     }]
    		           }]    	
    	});
    	
    	this.callParent(arguments);
    },
    
    reconfigure: function(store, columns) {
        var me = this,
            headerCt = me.headerCt;

        if (me.lockable) {
            me.reconfigureLockable(store, columns);
        } else {
            if (columns) {
                headerCt.suspendLayout = true;
                headerCt.removeAll();
                headerCt.add(columns);
            }
            if (store) {
                store = Ext.StoreManager.lookup(store);
                me.down('pagingtoolbar').bindStore(store);
                me.bindStore(store);        
            } else {
                me.getView().refresh();
            }
            if (columns) {
                headerCt.suspendLayout = false;
                me.forceComponentLayout();
            }
        }
        me.fireEvent('reconfigure', me);
    }

});