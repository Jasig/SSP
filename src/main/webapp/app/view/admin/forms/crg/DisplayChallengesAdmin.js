Ext.define('Ssp.view.admin.forms.crg.DisplayChallengesAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaychallengesadmin',
	title: 'Challenges Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.DisplayChallengesAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: me.authenticatedPerson.hasAccess('CHALLENGE_CATEGORIES_ADMIN_ASSOCIATIONS'),
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 1 
    		                },
    		                { header: 'Show On Intake',  
      		                  dataIndex: 'showInStudentIntake',
      		                  renderer: me.columnRendererUtils.renderFriendlyBoolean,
      		                  flex: 1 
      		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGES_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGES_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CHALLENGES_ADMIN_DELETE_BUTTON'),
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
    	
    	return me.callParent(arguments);
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