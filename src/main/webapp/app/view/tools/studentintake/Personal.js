Ext.define('Ssp.view.tools.studentintake.Personal', {
	extend: 'Ext.form.Panel',
	id: 'StudentIntakePersonal',
    url:'save-form.php',
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
        allowBlank:false
    },{
        fieldLabel: 'Middle Initial',
        name: 'middleInitial'
    },{
        fieldLabel: 'Last Name',
        name: 'lastName'
    },{
        fieldLabel: 'Tartan ID',
        name: 'schoolId'
    },{
        fieldLabel: 'Home Phone',
        name: 'homePhone',
        emptyText: 'xxx-xxx-xxxx',
        maskRe: /[\d\-]/,
        regex: /^\d{3}-\d{3}-\d{4}$/,
        regexText: 'Must be in the format xxx-xxx-xxxx'
    },{
        fieldLabel: 'Work Phone',
        name: 'workPhone',
        emptyText: 'xxx-xxx-xxxx',
        maskRe: /[\d\-]/,
        regex: /^\d{3}-\d{3}-\d{4}$/,
        regexText: 'Must be in the format xxx-xxx-xxxx'
    },{
        fieldLabel: 'Cell Phone',
        name: 'cellPhone',
        emptyText: 'xxx-xxx-xxxx',
        maskRe: /[\d\-]/,
        regex: /^\d{3}-\d{3}-\d{4}$/,
        regexText: 'Must be in the format xxx-xxx-xxxx'
    },{
        fieldLabel: 'Address',
        name: 'address'
    },{
        fieldLabel: 'City',
        name: 'city'
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
        allowBlank: false,
        forceSelection: true
	},{
        fieldLabel: 'Zip Code',
        name: 'zipCode'
    },{
        fieldLabel: 'Email School',
        name: 'primaryEmailAddress',
        vtype:'email'
    },{
        fieldLabel: 'Email Alternate',
        name: 'secondaryEmailAddress',
        vtype:'email'
    }]
    }],
    /*,

    buttons: [{
        text: 'Save'
    },{
        text: 'Cancel'
    }]
    */ 

});
