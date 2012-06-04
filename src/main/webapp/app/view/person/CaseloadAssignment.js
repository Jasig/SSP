Ext.define('Ssp.view.person.CaseloadAssignment', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.caseloadassignment',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.CaseloadAssignmentViewController',
	width: '100%',
	height: '100%',   
	initComponent: function() {	
		Ext.apply(this, 
				{
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
				        	     text:'Save', 
				        	     action: 'save' 
				        	    },
				                 {
				            	   xtype: 'button',
				            	   itemId: 'cancelButton',
				            	   text: 'Cancel',
				            	   action: 'cancel'
				                 }]
				    }],
				    
		    		items: [ { title: 'Personal',
			        	       autoScroll: true,
			        		   items: [{xtype: 'editperson'}]
			        		},{
			            		title: 'Appointment',
			            		autoScroll: true,
			            		items: [{xtype: 'personappointment'}]
			        		},{
			            		title: 'Special Service Groups',
			            		autoScroll: true,
			            		items: [{xtype: 'personspecialservicegroups'}]
			        		},{
			            		title: 'Referral Sources',
			            		autoScroll: true,
			            		items: [{xtype: 'personreferralsources'}]
			        		},{
			            		title: 'Reasons for Service',
			            		autoScroll: true,
			            		items: [{xtype: 'personservicereasons'}]
			        		},{
			            		title: 'Ability to Benefit/Anticipated Start Date',
			            		autoScroll: true,
			            		items: [{xtype: 'personanticipatedstartdate'}]
			        		}]
			});
	
		return this.callParent(arguments);
	}

});