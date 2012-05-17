Ext.define('Ssp.view.StudentRecord', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.studentrecord',
    id: 'StudentRecord',
    initComponent: function(){
    	Ext.apply(this,{
    		title: 'Student Record',
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