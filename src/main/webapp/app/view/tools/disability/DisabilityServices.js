Ext.define('Ssp.view.tools.disability.DisabilityServices', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.disabilityservices',
	title: 'Disability Services',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{		
					border: 0,
					dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				        			xtype: 'button', 
				        			itemId: 'saveButton', 
				        			text:'Save', 
				        			action: 'save'
				        	   }]
					}],
				    
				    items: [ Ext.createWidget('tabpanel', {
				        width: '100%',
				        height: '100%',
				        activeTab: 0,
						border: 0,
				        items: [ { title: 'General',
				        	       autoScroll: true,
				        		   items: [{xtype: 'disabilitygeneral'}]
				        		},{
				            		title: 'Agency Contacts',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'disabilityagencycontacts'}]
				        		},{
				            		title: 'Disability',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'disabilitycodes'}]
				        		},{
				            		title: 'Disposition',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'disabilitydisposition'}]
				        		},{
				            		title: 'Accommodations',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'disabilityaccommodations'}]
				        		}]
				    })]
			    
		});
		
		return me.callParent(arguments);
	}
});