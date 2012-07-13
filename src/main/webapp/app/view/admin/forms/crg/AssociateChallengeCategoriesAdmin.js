Ext.define('Ssp.view.admin.forms.crg.AssociateChallengeCategoriesAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengecategoriesadmin',
	title: 'Challenge Category Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.AssociateChallengeCategoriesAdminViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
        store: 'treeStore'
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
    		     singleExpand: true,
    			 store: me.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
			        plugins: {
			            ptype: 'treeviewdragdrop',
			            dropGroup: 'gridtotree',
			            enableDrop: true
			        }
			     },
    			 dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_CATEGORIES_ASSOCIATION_ADMIN_DELETE_BUTTON'),
     				            xtype: 'button',
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }] 
     		       	
    	});
    	
    	return me.callParent(arguments);
    }
});