Ext.define('Ssp.view.admin.forms.campus.CampusEarlyAlertRoutingsAdmin', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.campusearlyalertroutingsadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusEarlyAlertRoutingsAdminViewController',
	title: 'Define Campus Early Alert Routing Groups',
	height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{   
    		      items: [{
    		    	xtype:'earlyalertroutingsadmin',
    		    	flex: 1
    		      }]	
    	});

    	return me.callParent(arguments);
    }
});