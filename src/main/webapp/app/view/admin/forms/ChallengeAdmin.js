Ext.define('Ssp.view.admin.forms.ChallengeAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.challengeadmin',
	title: 'Admin',
	id: 'ChallengeAdmin',
	autoScroll: true,
	height: '100%',
	width: '100%',
    selType: 'rowmodel',

    initComponent: function(){
    	var cellEditor = Ext.create('Ext.grid.plugin.RowEditing', 
		                             { clicksToEdit: 2 });
    	Ext.apply(this,
    			{
    		      plugins:cellEditor,
    		      
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
    		                  flex: 50 },
      		                { header: 'Tags',
      		                  dataIndex: 'tags', 
      		                  flex: 50,
      		                  field: {
      		                      xtype: 'textfield'
      		                  }
    		                }
    		           ],
    		        
    		           dockedItems: [
    		       		{
    		       			xtype: 'pagingtoolbar',
    		       		    dock: 'bottom',
    		       		    displayInfo: true,
    		       		    pageSize: 15
    		       		},

    		              {
    		               xtype: 'toolbar',
    		               items: [{
    		                   text: 'Add',
    		                   iconCls: 'icon-add',
    		                   handler: function(){
    		                   	var item = new Ssp.model.reference.Challenge();
    		           			item.set('name','default');
    		                   	this.up('grid').getStore().insert(0, item );
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