Ext.define('Ssp.view.tools.profile.RecentSSPActivity', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.recentsspactivity',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    width: '100%',
    height: '100%',
    title: 'Recent SSP Activity for this Student',
    autoScroll: true,
    inject: {
        //store: 'recentActivityStore'
    },
    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            //store: me.store,
            xtype: 'gridcolumn',
            columns: [{
                dataIndex: 'coach',
                text: 'Coach',
				flex: 1
            }, {
                dataIndex: 'service',
                text: 'Service',
				flex: 1
            }, {
            
                dataIndex: 'recentDate',
                text: 'Date',
				flex: 1
            }],
            viewConfig: {}
        });
        
        me.callParent(arguments);
    }
});