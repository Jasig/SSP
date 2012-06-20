Ext.define('Ssp.view.admin.forms.crg.AssociateChallengeReferralsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengereferralsadmin',
	title: 'Challenge Referral Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.AssociateChallengeReferralsAdminViewController',
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
    			 singleExpand: true,
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
     		              dock: 'top',
     		               items: [{
     		                         xtype: 'label',
     		                         text: 'Associate items by dragging a Referral onto a Challenge folder'
     		                       }]  
     		           },{
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