Ext.define('Ssp.view.person.EditPerson', {
	extend: 'Ext.form.Panel',
	alias: 'widget.editperson',
	initComponent: function() {	
		Ext.apply(this, 
				{
					border: 0,	    
					items: [{
			            xtype: 'fieldset',
			            border: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			       items: [{
			        fieldLabel: 'First Name',
			        name: 'firstName',
			        itemId: 'firstName',
			        maxLength: 50,
			        allowBlank:false
			    },{
			        fieldLabel: 'Middle Initial',
			        name: 'middleInitial',
			        itemId: 'middleInitial',
			        maxLength: 1,
			        allowBlank:true
			    },{
			        fieldLabel: 'Last Name',
			        name: 'lastName',
			        itemId: 'lastName',
			        maxLength: 50,
			        allowBlank:false
			    },{
			        fieldLabel: 'Student ID',
			        name: 'schoolId',
			        minLength: 0,
			        maxLength: 3,
			        itemId: 'studentId',
			        allowBlank:false
			    },{
			        fieldLabel: 'Home Phone',
			        name: 'homePhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maskRe: /[\d\-]/,
			        regex: /^\d{3}-\d{3}-\d{4}$/,
			        regexText: 'Must be in the format xxx-xxx-xxxx',
			        maxLength: 12,
			        allowBlank:true,
			        itemId: 'homePhone' 
			    },{
			        fieldLabel: 'Work Phone',
			        name: 'workPhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maskRe: /[\d\-]/,
			        regex: /^\d{3}-\d{3}-\d{4}$/,
			        regexText: 'Must be in the format xxx-xxx-xxxx',
			        maxLength: 12,
			        allowBlank:true,
			        itemId: 'workPhone'
			    },{
			        fieldLabel: 'School Email',
			        name: 'primaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'primaryEmailAddress'
			    },{
			        fieldLabel: 'Home Email',
			        name: 'secondaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'secondaryEmailAddress'
			    }]
			}]
		});
		
		return this.callParent(arguments);
	}
});