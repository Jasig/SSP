Ext.define('Ssp.view.admin.forms.journal.AssociateTrackStepsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.associatetrackstepsadmin',
	title: 'Track Steps Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.AssociateTrackStepsAdminViewController',
    inject: {
        store: 'treeStore'
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
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