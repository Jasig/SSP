Ext.define('Ssp.view.StudentRecord', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.studentrecord',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.StudentRecordViewController',
    width: '100%',
    height: '100%',
    initComponent: function(){
    	Ext.apply(this,{
    		title: 'Student Record',
    	    collapsible: true,
    	    collapseDirection: 'left',
    		layout: {
    	    	type: 'hbox',
    	    	align: 'stretch'
    	    },
			
    	    items: [{xtype:'toolsmenu',flex:1},
			        {xtype: 'tools', flex:4}]		        
    	});
    	return this.callParent(arguments);
    }
});	