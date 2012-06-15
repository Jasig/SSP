Ext.define('Ssp.view.tools.Profile', {
	extend: 'Ext.form.Panel',
	alias : 'widget.profile',
	id: 'Profile',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.ProfileToolViewController',
    width: '100%',
	height: '100%',
    initComponent: function() {	
		var me=this;
    	Ext.apply(me, 
				{
		    	    layout: 'anchor',
		            title: 'Profile',
		            padding: 0,
		            border: 0,
					items: [
						Ext.createWidget('tabpanel', {
						    width: '100%',
						    height: '100%',
						    activeTab: 0,
						    itemId: 'profileTabs',
						    items: [{ 
						    	      title: 'Student',
						    	      autoScroll: true,
						    		  items: [{xtype: 'profileperson'}]
						    		},{ 
						    		  title: 'Special Service Groups',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profilespecialservicegroups'}]
						    		},{ 
						    		  title: 'Referral Sources',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profilereferralsources'}]
						    		}]
						})
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
					            tooltip: 'View Student History',
					            text: 'View History',
					            xtype: 'button',
					            itemId: 'viewHistoryButton'
					        }]
				    }]
				});	     
    	
    	return this.callParent(arguments);
	}
});