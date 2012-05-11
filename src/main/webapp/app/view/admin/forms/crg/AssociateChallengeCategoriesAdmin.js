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
     	                       text: 'Associate by dragging a Challenge to a folder'
     	                     }]
     		           } ] 
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});