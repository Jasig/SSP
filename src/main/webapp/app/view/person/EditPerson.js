Ext.define('Ssp.view.person.EditPerson', {
	extend: 'Ext.form.Panel',
	alias: 'widget.editperson',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.EditPersonViewController',
	initComponent: function() {	
		Ext.apply(this, 
				{
					border: 0,	    
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 100
				    },
					items: [{
			            xtype: 'fieldset',
			            border: 0,
			            title: '',
			            defaultType: 'textfield',

			       items: [{
			        fieldLabel: 'First Name',
			        name: 'firstName',
			        itemId: 'firstName',
			        id: 'editPersonFirstName',
			        maxLength: 50,
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Middle Initial',
			        name: 'middleName',
			        itemId: 'middleName',
			        id: 'editPersonMiddleName',
			        maxLength: 1,
			        allowBlank:true,
			        width: 350
			    },{
			        fieldLabel: 'Last Name',
			        name: 'lastName',
			        itemId: 'lastName',
			        id: 'editPersonLastName',
			        maxLength: 50,
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Student ID',
			        name: 'schoolId',
			        minLength: 7,
			        maxLength: 7,
			        itemId: 'studentId',
			        allowBlank:false,
			        width: 350
			    },{
			    	xtype: 'button',
			    	tooltip: 'Load record from external system',
			    	text: 'Retrieve from SIS',
			    	itemId: 'retrieveFromExternalButton'
			    },{
			        fieldLabel: 'Home Phone',
			        name: 'homePhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maskRe: /[\d\-]/,
			        regex: /^\d{3}-\d{3}-\d{4}$/,
			        regexText: 'Must be in the format xxx-xxx-xxxx',
			        maxLength: 12,
			        allowBlank:true,
			        itemId: 'homePhone',
			        width: 350
			    },{
			        fieldLabel: 'Work Phone',
			        name: 'workPhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maskRe: /[\d\-]/,
			        regex: /^\d{3}-\d{3}-\d{4}$/,
			        regexText: 'Must be in the format xxx-xxx-xxxx',
			        maxLength: 12,
			        allowBlank:true,
			        itemId: 'workPhone',
			        width: 350
			    },{
			        fieldLabel: 'School Email',
			        name: 'primaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'primaryEmailAddress',
			        width: 350
			    },{
			        fieldLabel: 'Home Email',
			        name: 'secondaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'secondaryEmailAddress',
			        width: 350
			    }]
			}]
		});
		
		return this.callParent(arguments);
	}
});