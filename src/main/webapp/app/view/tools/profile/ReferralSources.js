Ext.define('Ssp.view.tools.profile.ReferralSources', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profilereferralsources',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	store: 'profileReferralSourcesStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
			        hideHeaders: true,
			        autoScroll: true,
		            store: me.store,
    		        columns: [
    		                { header: 'Source',  
    		                  dataIndex: 'name',
    		                  flex: 1,
    		                }],
				});
		
		return me.callParent(arguments);
	}
});