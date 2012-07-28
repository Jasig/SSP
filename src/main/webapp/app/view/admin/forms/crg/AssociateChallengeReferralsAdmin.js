Ext.define('Ssp.view.admin.forms.crg.AssociateChallengeReferralsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengereferralsadmin',
	title: 'Challenge Referral Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.AssociateChallengeReferralsAdminViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
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
    	var me=this;
    	Ext.apply(me,
    			{
    		     autoScroll: true,
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
     				            xtype: 'button',
     				            hidden: !me.authenticatedPerson.hasAccess('CHALLENGE_REFERRALS_ASSOCIATION_ADMIN_DELETE_BUTTON'),
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }]
     		       	
    	});
    	
    	return me.callParent(arguments);
    }
});