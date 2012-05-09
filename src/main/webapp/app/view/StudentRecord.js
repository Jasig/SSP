Ext.define('Ssp.view.StudentRecord', {
	extend: 'Ext.container.Container',
    alias: 'widget.studentrecord',
    id: 'StudentRecord',
    initComponent: function(){
    	Ext.apply(this,{
    	    collapsible: true,
    	    collapseDirection: 'right',
    	    layout: {
    	    	type: 'hbox',
    	    	align: 'stretch'
    	    }    		
    	});
    	return this.callParent(arguments);
    }
});	