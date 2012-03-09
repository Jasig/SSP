Ext.define('Ssp.view.tools.studentintake.Personal', {
	extend: 'Ext.form.Panel',
	id: 'StudentIntakePersonal',
	autoScroll: true,
    width: '100%',
    height: '100%',
    fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 100
    },
    
    stores: ['reference.States'],
    
    defaultType: 'textfield',
    defaults: {
        anchor: '100%'
    },
       
    items: [{
            xtype: 'fieldset',
            title: 'Personal Details',
            defaultType: 'textfield',
            defaults: {
                anchor: '100%'
            },
       items: [{
    	xtype: 'displayfield',
        fieldLabel: 'Intake Date',
        name: 'studentIntakeCreatedDate'
    },{
        fieldLabel: 'First Name',
        name: 'firstName',
        maxLength: 50,
        allowBlank:false
    },{
        fieldLabel: 'Middle Initial',
        name: 'middleInitial',
        maxLength: 1,
        allowBlank:true
    },{
        fieldLabel: 'Last Name',
        name: 'lastName',
        maxLength: 50,
        allowBlank:false
    },{
        fieldLabel: 'Tartan ID',
        name: 'schoolId',
        minLength: 7,
        maxLength: 7,
        allowBlank:false
    },{
        fieldLabel: 'Birth Date',
        name: 'birthDate',
        vtype:'date',
        maxLength: 10,
        allowBlank:false    	
    },{
        fieldLabel: 'Home Phone',
        name: 'homePhone',
        emptyText: 'xxx-xxx-xxxx',
        maskRe: /[\d\-]/,
        regex: /^\d{3}-\d{3}-\d{4}$/,
        regexText: 'Must be in the format xxx-xxx-xxxx',
        maxLength: 12,
        allowBlank:true
    },{
        fieldLabel: 'Work Phone',
        name: 'workPhone',
        emptyText: 'xxx-xxx-xxxx',
        maskRe: /[\d\-]/,
        regex: /^\d{3}-\d{3}-\d{4}$/,
        regexText: 'Must be in the format xxx-xxx-xxxx',
        maxLength: 12,
        allowBlank:true
    },{
        fieldLabel: 'Cell Phone',
        name: 'cellPhone',
        emptyText: 'xxx-xxx-xxxx',
        maskRe: /[\d\-]/,
        regex: /^\d{3}-\d{3}-\d{4}$/,
        regexText: 'Must be in the format xxx-xxx-xxxx',
        maxLength: 12,
        allowBlank:true
    },{
        fieldLabel: 'Address',
        name: 'address',
        maxLength: 50,
        allowBlank:true
    },{
        fieldLabel: 'City',
        name: 'city',
        maxLength: 50,
        allowBlank:true
    },{
        xtype: 'combobox',
        name: 'state',
        fieldLabel: 'State',
        emptyText: 'Select a State',
        store: Ext.getStore('reference.States'),
        valueField: 'abbr',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true,
        forceSelection: true
	},{
        fieldLabel: 'Zip Code',
        name: 'zipCode',
        maxLength: 10,
        allowBlank:true
    },{
        fieldLabel: 'Email School',
        name: 'primaryEmailAddress',
        vtype:'email',
        maxLength: 100,
        allowBlank:true
    },{
        fieldLabel: 'Email Alternate',
        name: 'secondaryEmailAddress',
        vtype:'email',
        maxLength: 100,
        allowBlank:true
    }]
    }] 

});
