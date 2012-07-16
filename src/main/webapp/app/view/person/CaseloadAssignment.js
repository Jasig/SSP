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
		var me=this;
		Ext.apply(me, 
				{
			        title: "Caseload Assignment",
		    		autoScroll: true,
		    	    defaults: {
		    	        bodyStyle: 'padding:5px'
		    	    },
		    	    layout: {
		    	        type: 'accordion',
		    	        align: 'stretch',
		    	        titleCollapse: true,
		    	        animate: true,
		    	        activeOnTop: true
		    	    },		    		
		    		dockedItems: [{
  		               xtype: 'toolbar',
  		               dock: 'top',
  		               items: [{
  		                         xtype: 'label',
  		                         text: 'Fill out the following forms with assigned coach details and appointment information'
  		                       }]  
  		            },{
				        dock: 'bottom',
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
						        	width: 50
						         }/*,{
							        xtype: 'checkbox',
							        boxLabel: 'Send Student Intake Request', 
							        name: 'sendStudentIntakeRequest'
								 },{ 
						        	xtype: 'tbspacer',
						        	width: 25
						         },{
							    	xtype: 'displayfield',
							        fieldLabel: 'Last Request Date',
							        name: 'lastStudentIntakeRequestDate',
							        value: ((me.model.getFormattedStudentIntakeRequestDate().length > 0) ? me.person.getFormattedStudentIntakeRequestDate() : 'No requests have been sent')
								 },{ 
						        	xtype: 'tbspacer',
						        	flex: 1
						         }*/,
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
	
		return me.callParent(arguments);
	}

});