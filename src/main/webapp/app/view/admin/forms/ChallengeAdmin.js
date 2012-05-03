Ext.define('Ssp.view.admin.forms.ChallengeAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.ChallengeAdmin',
	title: 'Admin',
	id: 'ChallengeAdmin',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	height: '100%',
	width: '100%',

    initComponent: function(){
    	var cellEditor = Ext.create('Ext.grid.plugin.RowEditing', 
		                             { clicksToEdit: 2 });
    	Ext.apply(this,
    			{
    		      autoScroll: true,
    		      plugins:cellEditor,
    		      selType: 'rowmodel',

    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1 
    		                },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 1,
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1 
    		                },
    		                {
    		                	header: 'Show In Intake',
    		                	dataIndex: 'showInStudentIntake',
    		                	xtype: 'checkcolumn',
    		                	field: {
    		                		xtype: 'checkbox'
    		                	}
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
     		                   action: 'add'
     		               }, '-', {
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   action: 'delete'
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