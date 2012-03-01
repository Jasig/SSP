Ext.define('Ssp.view.tools.studentintake.Demographics', {
	extend: 'Ext.form.Panel',
	id : 'StudentIntakeDemographics',
    width: '100%',
    height: '100%',

	stores: ['reference.ChildCareArrangements',
			 'reference.Citizenships',
			 'reference.EducationalGoals',
			 'reference.EducationLevels',
			 'reference.EmploymentShifts',
			 'reference.Ethnicities',
			 'reference.Genders',
			 'reference.MaritalStatuses',
			 'reference.YesNo'],		

    // Fields will be arranged vertically, stretched to full width
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },

    // The fields
    defaultType: 'displayfield',
    items: [{
            xtype: 'fieldset',
            title: 'Demographic Details',
            defaultType: 'textfield',
            defaults: {
                anchor: '100%'
            },
       items: [{
        xtype: 'combobox',
        name: 'maritalStatus',
        fieldLabel: 'Marital Status',
        emptyText: 'Select One',
        store: Ext.getStore('reference.MaritalStatuses'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: false,
        forceSelection: true
	},{
        xtype: 'combobox',
        name: 'ethnicity',
        fieldLabel: 'Ethnicity',
        emptyText: 'Select One',
        store: Ext.getStore('reference.Ethnicities'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        xtype: 'combobox',
        name: 'gender',
        fieldLabel: 'Gender',
        emptyText: 'Select One',
        store: Ext.getStore('reference.Genders'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        xtype: 'combobox',
        name: 'citizenship',
        fieldLabel: 'Citizenship',
        emptyText: 'Select One',
        store: Ext.getStore('reference.Citizenships'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        xtype: 'combobox',
        name: 'veteranStatus',
        fieldLabel: 'Veteran Status',
        emptyText: 'Select One',
        store: Ext.getStore('reference.VeteranStatuses'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        xtype: 'combobox',
        name: 'primaryCaregiver',
        fieldLabel: 'Are you a primary caregiver?',
        emptyText: 'Select One',
        store: Ext.getStore('reference.YesNo'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	}, {
        xtype: 'displayfield',
        fieldLabel: 'If you have children, please indicate below'
    },{
        xtype: 'numberfield',
        name: 'numberOfChildren',
        fieldLabel: 'How many?',
        value: 0,
        minValue: 0,
        maxValue: 50
    },{
        fieldLabel: 'Ages? Separate each age with a comma. (1,5,12)',
        name: 'childAges'
    },{
        xtype: 'combobox',
        name: 'childCareNeeded',
        fieldLabel: 'Childcare needed?',
        emptyText: 'Select One',
        store: Ext.getStore('reference.YesNo'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        xtype: 'combobox',
        name: 'childCareArrangement',
        fieldLabel: 'If yes, what are your childcare arrangments?',
        emptyText: 'Select One',
        store: Ext.getStore('reference.ChildCareArrangements'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        xtype: 'combobox',
        name: 'employed',
        fieldLabel: 'Are you employed?',
        emptyText: 'Select One',
        store: Ext.getStore('reference.YesNo'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        fieldLabel: 'Place of employment',
        name: 'placeOfEmployment'
    },{
        xtype: 'combobox',
        name: 'shift',
        fieldLabel: 'Shift',
        emptyText: 'Select One',
        store: Ext.getStore('reference.EmploymentShifts'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        fieldLabel: 'Wage',
        name: 'wage'
    },{
        fieldLabel: 'Total hours worked weekly while attending school',
        name: 'totalHoursWorkedPerWeek'
    }]
    }],
	

	initComponent: function() {	
		this.superclass.initComponent.call(this, arguments);
	}
	
});