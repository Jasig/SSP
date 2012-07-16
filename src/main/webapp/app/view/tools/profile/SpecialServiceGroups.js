Ext.define('Ssp.view.tools.profile.SpecialServiceGroups', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profilespecialservicegroups',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	store: 'profileSpecialServiceGroupsStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
			        hideHeaders: true,
			        store: me.store,
					autoScroll: true,
    		        columns: [
    		                { header: 'Group',  
    		                  dataIndex: 'name',
    		                  flex: 1,
    		                }],
				});
		
		return me.callParent(arguments);
	}
});