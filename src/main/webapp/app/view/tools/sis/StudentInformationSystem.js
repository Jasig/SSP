Ext.define('Ssp.view.tools.sis.StudentInformationSystem', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentinformationsystem',
	title: 'Student Information System',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{		
					border: 0,
				    items: [ Ext.createWidget('tabpanel', {
				        width: '100%',
				        height: '100%',
				        activeTab: 0,
						border: 0,
				        items: [ { title: 'Registration',
				        	       autoScroll: true,
				        		   items: [{xtype: 'sisregistration'}]
				        		},{
				            		title: 'Transcript',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'sistranscript'}]
				        		},{
				            		title: 'Assessment',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'sisassessment'}]
				        		}]
				    })]
			    
		});
		
		return me.callParent(arguments);
	}
});