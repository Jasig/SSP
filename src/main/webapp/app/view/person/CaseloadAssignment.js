Ext.define('Ssp.view.person.CaseloadAssignment', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.caseloadassignment',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.CaseloadAssignmentViewController',
    inject: {
    	model: 'currentPerson'
    },
    width: '100%',
	height: '100%',   
	initComponent: function() {
		Ext.apply(this, 
				{
			        title: "Caseload Assignment",
		    		autoScroll: true,
		    	    defaults: {
		    	        bodyStyle: 'padding:15px'
		    	    },
		    	    layout: {
		    	        type: 'accordion',
		    	        titleCollapse: true,
		    	        animate: true,
		    	        activeOnTop: true
		    	    },		    		
		    		
		    		dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{xtype: 'button', 
				        	     itemId: 'saveButton', 
				        	     text:'Save'
				        	    },
				                 {
				            	   xtype: 'button',
				            	   itemId: 'cancelButton',
				            	   text: 'Cancel',
				                 },{ 
						        	xtype: 'tbspacer',
						        	flex: 1
						         },
				                 {
				            	   xtype: 'button',
				            	   itemId: 'printButton',
				            	   tooltip: 'Print Appointment Form',
				            	   width: 30,
						           height: 30,
						           cls: 'printIcon'
				                 },
				                 {
				            	   xtype: 'button',
				            	   itemId: 'emailButton',
				            	   tooltip: 'Email Appointment Form',
				            	   width: 30,
						           height: 30,
						           cls: 'emailIcon'
				                 }]
				    }],
				    
				    items: [ ]
			});
	
		return this.callParent(arguments);
	}

});