Ext.define('Ssp.view.tools.studentintake.StudentIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentintake',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.StudentIntakeToolViewController',
    inject: {
        store: 'studentsStore'
    },
	title: 'Student Intake',	
	width: '100%',
	height: '100%',   
	initComponent: function() {	
		Ext.apply(this, 
				{
		    		store: this.store,
		    		layout: 'fit',
		    		padding: 0,
		    		border: 0,
		    		items: [],
						
			    		dockedItems: [{
					        dock: 'top',
					        xtype: 'toolbar',
					        items: [{xtype: 'button', itemId: 'saveButton', text:'Save', action: 'save' },
					                {xtype: 'button', itemId: 'cancelButton', text:'Cancel', action: 'reset' },
					                { 
					        	     xtype: 'tbspacer',
					        	     flex: 1
					               },{
					            	   xtype: 'button',
					            	   itemId: 'viewConfidentialityAgreementButton',
					            	   text: 'View Confidentiality Agreement',
					            	   action: 'viewConfidentialityAgreement'}]
					    }]

			});
						
		return this.callParent(arguments);
	}

});