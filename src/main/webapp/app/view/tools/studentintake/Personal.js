Ext.define('Ssp.view.tools.studentintake.Personal', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakepersonal',
	id: 'StudentIntakePersonal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.PersonalViewController',
    inject: {
        statesStore: 'statesStore'
    },
	width: '100%',
    height: '100%',    
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
					autoScroll: true,
    		        border: 0,	
				    bodyPadding: 5,				    
					layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 150
				    },
				    items: [{
				            xtype: 'fieldset',
				            border: 0,
				            title: '',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '95%'
				            },
				       items: [/*{
				    	xtype: 'displayfield',
				        fieldLabel: 'Intake Date',
				        name: 'studentIntakeCreatedDate'
				    },*/{
				    	xtype: 'displayfield',
				        fieldLabel: 'Intake Completion Date',
				        name: 'studentIntakeCompleteDate',
				        renderer: Ext.util.Format.dateRenderer('m/d/Y')
				    }/*,{
				    	xtype: 'displayfield',
				        fieldLabel: 'Agreed to Confidentiality',
				        name: 'confidentialityAgreement'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Date of Agreement',
				        name: 'confidentialityAgreementDate'
				    }*/,{
				        fieldLabel: 'First Name',
				        name: 'firstName',
				        itemId: 'firstName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: 'Middle Name',
				        name: 'middleName',
				        itemId: 'middleName',
				        maxLength: 50,
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
				        maxLength: 7,
				        itemId: 'studentId',
				        allowBlank:false
				    },{
				    	xtype: 'datefield',
				    	fieldLabel: 'Birth Date',
				    	itemId: 'birthDate',
				    	altFormats: 'm/d/Y|m-d-Y',
				    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
				        name: 'birthDate',
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
				        fieldLabel: 'Cell Phone',
				        name: 'cellPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maskRe: /[\d\-]/,
				        regex: /^\d{3}-\d{3}-\d{4}$/,
				        regexText: 'Must be in the format xxx-xxx-xxxx',
				        maxLength: 12,
				        allowBlank:true,
				        itemId: 'cellPhone'
				    },{
				        fieldLabel: 'Address',
				        name: 'addressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'address'
				    },{
				        fieldLabel: 'City',
				        name: 'city',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'city'
				    },{
				        xtype: 'combobox',
				        name: 'state',
				        fieldLabel: 'State',
				        emptyText: 'Select a State',
				        store: me.statesStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true,
				        forceSelection: true,
				        itemId: 'state'
					},{
				        fieldLabel: 'Zip Code',
				        name: 'zipCode',
				        maxLength: 10,
				        allowBlank:true,
				        itemId: 'zipCode'
				    },{
				        fieldLabel: 'Primary Email (School)',
				        name: 'primaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:true,
				        itemId: 'primaryEmailAddress'
				    },{
				        fieldLabel: 'Alternate Email',
				        name: 'secondaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:true,
				        itemId: 'secondaryEmailAddress'
				    }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});
