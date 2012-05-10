Ext.define('Ssp.view.admin.forms.crg.DisplayChallengeCategoriesAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengecategoriesadmin',
	title: 'Challenge Category Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.DisplayChallengeCategoriesAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        challengeCategoriesTreeStore: 'challengeCategoriesTreeStore'
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    			 store: this.challengeCategoriesTreeStore,
    			 useArrows: true,
    			 rootVisible: false,
    		     dockedItems: [
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
     		           }]    	
    	});
    	
    	return this.callParent(arguments);
    }
});