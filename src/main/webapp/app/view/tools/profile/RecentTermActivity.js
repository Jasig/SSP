Ext.define('Ssp.view.tools.profile.RecentTermActivity', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.recenttermactivity',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    width: '100%',
    height: '100%',
    title: 'Recent Term Activity',
    autoScroll: true,
    inject: {        //store: 'recentTermActivityStore'
    },
    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            //store: me.store,
            xtype: 'gridcolumn',
            columns: [{
                dataIndex: 'term',
                text: 'Term',
                flex: 1
            }, {
                dataIndex: 'onPlan',
                text: 'MAP',
                flex: 1
            }, {
            
                dataIndex: 'cumGPA',
                text: 'GPA',
                flex: 1
            }, {
            
                dataIndex: 'load',
                text: 'Load',
                flex: 1
            }],
            viewConfig: {}
        });
        
        me.callParent(arguments);
    }
});
