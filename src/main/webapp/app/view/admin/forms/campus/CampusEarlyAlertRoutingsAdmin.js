Ext.define('Ssp.view.admin.forms.campus.CampusEarlyAlertRoutingsAdmin', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.campusearlyalertroutingsadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusEarlyAlertRoutingsAdminViewController',
	inject: {
		campus: 'currentCampus'
	},
    height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{  
    			  title: 'Campuses Admin - ' + me.campus.get('name'),
    		      items: [{
    		    	xtype:'earlyalertroutingsadmin',
    		    	flex: 1
    		      }],
  	            
    		      dockedItems: [{
		               xtype: 'toolbar',
		               items: [{
	       		                   text: 'Finished',
	       		                   xtype: 'button',
	       		                   itemId: 'finishButton'
	       		               }]
		           }]
    	});

    	return me.callParent(arguments);
    }
});