Ext.define('Ssp.view.tools.studentintake.StudentIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentintake',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.StudentIntakeToolViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
        store: 'studentsStore'
    },
	title: 'Student Intake',	
	width: '100%',
	height: '100%',   
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
		    		store: me.store,
		    		layout: 'fit',
		    		padding: 0,
		    		border: 0,
		    		items: [],
						
			    		dockedItems: [{
					        dock: 'top',
					        xtype: 'toolbar',
					        items: [{
					        	     xtype: 'button', 
					        	     itemId: 'saveButton', 
					        	     text:'Save', 
					        	     action: 'save',
					        	     hidden: !me.authenticatedPerson.hasAccess('STUDENT_INTAKE_SAVE_BUTTON'),
					        	    },
					                {
					        	     xtype: 'button', 
					        	     itemId: 'cancelButton', 
					        	     text:'Cancel', 
					        	     action: 'reset',
					        	     hidden: !me.authenticatedPerson.hasAccess('STUDENT_INTAKE_CANCEL_BUTTON'),
					        	    },
					                { 
					        	     xtype: 'tbspacer',
					        	     flex: 1
					               },{
					            	   xtype: 'button',
					            	   itemId: 'viewConfidentialityAgreementButton',
					            	   text: 'View Confidentiality Agreement',
					            	   action: 'viewConfidentialityAgreement',
					            	   hidden: !me.authenticatedPerson.hasAccess('STUDENT_INTAKE_PRINT_CONFIDENTIALITY_AGREEMENT_BUTTON'),
					               }]
					    }]

			});
						
		return me.callParent(arguments);
	}

});