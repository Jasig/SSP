Ext.define('Ssp.view.tools.profile.ServiceReasons', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profileservicereasons',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	store: 'profileServiceReasonsStore'
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
    		                { header: 'Reason',  
    		                  dataIndex: 'name',
    		                  flex: 1,
    		                }],
				});
		
		return me.callParent(arguments);
	}
});