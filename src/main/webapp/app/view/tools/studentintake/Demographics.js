Ext.define('Ssp.view.tools.studentintake.Demographics', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakedemographics',
	id : 'StudentIntakeDemographics',
    width: '100%',
    height: '100%',
    autoScroll: true,
	stores: ['reference.ChildCareArrangements',
			 'reference.Citizenships',
			 'reference.EducationalGoals',
			 'reference.EducationLevels',
			 'reference.EmploymentShifts',
			 'reference.Ethnicities',
			 'reference.Genders',
			 'reference.MaritalStatuses',
			 'reference.YesNo'],		

    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },

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
        name: 'maritalStatusId',
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
        name: 'ethnicityId',
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
        valueField: 'code',
        displayField: 'title',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        xtype: 'combobox',
        name: 'citizenshipId',
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
        fieldLabel: 'Country of citizenship',
        name: 'countryOfCitizenship'
    },{
        xtype: 'combobox',
        name: 'veteranStatusId',
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
        xtype: "radiogroup",
        fieldLabel: "Are you a Primary Caregiver?",
        columns: 1,
        items: [
            {boxLabel: "Yes", name: "primaryCaregiver", inputValue:"true"},
            {boxLabel: "No", name: "primaryCaregiver", inputValue:"false"}]
    },{
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
        xtype: "radiogroup",
        fieldLabel: "Childcare Needed?",
        columns: 1,
        items: [
            {boxLabel: "Yes", name: "childCareNeeded", inputValue:"true"},
            {boxLabel: "No", name: "childCareNeeded", inputValue:"false"}]
    },{
        xtype: 'combobox',
        name: 'childCareArrangementId',
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
        xtype: "radiogroup",
        fieldLabel: "Are you employed?",
        columns: 1,
        items: [
            {boxLabel: "Yes", name: "employed", inputValue:"true"},
            {boxLabel: "No", name: "employed", inputValue:"false"}]
    },{
        fieldLabel: 'Place of employment',
        name: 'placeOfEmployment'
    },{
        xtype: 'combobox',
        name: 'shift',
        fieldLabel: 'Shift',
        emptyText: 'Select One',
        store: Ext.getStore('reference.EmploymentShifts'),
        valueField: 'code',
        displayField: 'title',
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
    }]
	
});