Ext.define('Ssp.view.admin.forms.crg.AssociateChallengeCategoriesAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengecategoriesadmin',
	title: 'Challenge Category Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.AssociateChallengeCategoriesAdminViewController',
    inject: {
        store: 'treeStore'
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    		     singleExpand: true,
    			 store: this.store,
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
     				            xtype: 'button',
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }] 
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});