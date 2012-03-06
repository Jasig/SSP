Ext.define('Ssp.view.admin.forms.AbstractReferenceAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.AbstractReferenceAdmin',
	title: 'Admin',
	id: 'AbstractReferenceAdmin',
    // store: Ext.getStore('reference.Challenges'),
    plugins: [Ext.create('Ext.grid.plugin.RowEditing',{id:'rowEditing'})], 
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
            	var grid = Ext.getCmp('AbstractReferenceAdmin');
    			grid.getStore().insert(0, new Ssp.model.reference.AbstractReferenceTO() );
    			grid.getPlugin('rowEditing').startEdit(0, 0);
            }
        }, '-', {
            text: 'Delete',
            iconCls: 'icon-delete',
            handler: function(){
            	var grid = Ext.getCmp('AbstractReferenceAdmin');
                var selection = grid.getView().getSelectionModel().getSelection()[0];
                if (selection) {
                	grid.getStore().remove(selection);
                }
            }
        }]
    }]

});