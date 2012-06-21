Ext.define('Ssp.view.admin.forms.crg.DisplayChallengesAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaychallengesadmin',
	title: 'Challenges Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.DisplayChallengesAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	Ext.apply(this,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: true
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1 
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
     		               }, '-' ,{
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
     	                       text: 'Associate items by dragging a Challenge onto a Category folder'
     	                     }]
     		           }]    	
    	});
    	
    	return this.callParent(arguments);
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