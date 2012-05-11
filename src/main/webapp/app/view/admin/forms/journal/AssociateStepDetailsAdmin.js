Ext.define('Ssp.view.admin.forms.journal.AssociateStepDetailsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.associatestepdetailsadmin',
	title: 'Step Details Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController',
    inject: {
        store: 'treeStore'
    },
    viewConfig: {
        plugins: {
            ptype: 'treeviewdragdrop'
        }
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    		     autoScroll: true,
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
			     viewConfig: {
				        plugins: {
				            ptype: 'treeviewdragdrop',
				            dropGroup: 'gridtotree',
				            enableDrop: true
				        }
				 },    			 
    		     dockedItems: [
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                         xtype: 'label',
     		                         text: 'Associate by dragging a Detail to a folder'
     		                       }]  
     		           }]
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});