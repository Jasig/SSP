Ext.define('Ssp.view.tools.profile.Profile', {
	extend: 'Ext.form.Panel',
	alias : 'widget.profile',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileToolViewController',
    width: '100%',
	height: '100%',
    initComponent: function() {	
		var me=this;
    	Ext.apply(me, 
				{
		    	    layout: 'fit',
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
						    	      title: 'Personal',
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
						    		},{ 
							    		  title: 'Services Provided History',
							    		  hidden: true,
							    		  autoScroll: true,
							    		  items: [{xtype: 'profileservicesprovided'}]
							    	}]
						})
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Transition Student',
				            text: '',
				            width: 35,
				            height: 35,
				            cls: 'studentTransitionIcon',
				            xtype: 'button',
				            itemId: 'studentTransitionButton'
				        },{
					            tooltip: 'View Student History',
					            text: '',
					            width: 35,
					            height: 35,
					            cls: 'studentHistoryIcon',
					            xtype: 'button',
					            itemId: 'viewHistoryButton'
					        }]
				    }]
				});	     
    	
    	return this.callParent(arguments);
	}
});