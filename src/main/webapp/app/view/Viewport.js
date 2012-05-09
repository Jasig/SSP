Ext.define('Ssp.view.Viewport',{
	extend: 'Ext.container.Viewport',
    layout: 'fit',
    id: 'sspView',
    alias: 'widget.sspview',
    initComponent: function() {
    	Ext.apply(this,{items: [{xtype:'Main'}]})
    	return this.callParent(arguments);
    }
});