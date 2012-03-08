Ext.define('Ssp.view.admin.forms.AbstractReferenceAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.AbstractReferenceAdmin',
	title: 'Admin',
	id: 'AbstractReferenceAdmin',
    plugins: [
		Ext.create('Ext.grid.plugin.RowEditing', { 
			id: 'AbstractReferenceAdminRowEditing',
		    clicksToEdit: 2
		})
    ],
    selType: 'rowmodel',
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
           }
         }
    ],  
    
    dockedItems: [{
        xtype: 'toolbar',
        items: [{
            text: 'Add',
            iconCls: 'icon-add',
            handler: function(){
            	var abstractReferenceTO = new Ssp.model.reference.AbstractReferenceTO();
    			abstractReferenceTO.set('name','default');
            	this.up('grid').getStore().insert(0, abstractReferenceTO );
            	this.up('grid').plugins[0].startEdit(0, 0);
            }
        }, '-', {
            text: 'Delete',
            iconCls: 'icon-delete',
            handler: function(){
            	var selection = this.up('grid').getView().getSelectionModel().getSelection()[0];
                if (selection) {
                	this.up('grid').getStore().remove( selection );           	
                }
            }
        }]
    }],
    
    initComponent: function(){
    	this.callParent();
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