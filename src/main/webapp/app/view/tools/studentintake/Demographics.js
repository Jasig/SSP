Ext.define('Ssp.view.tools.studentintake.Demographics', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakedemographics',
	id : 'StudentIntakeDemographics',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.DemographicsViewController',
    inject: {
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	gendersStore: 'gendersStore',
    	maritalStatusesStore: 'maritalStatusesStore',
    	veteranStatusesStore: 'veteranStatusesStore'
    },    
	width: '100%',
    height: '100%',
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
					autoScroll: true,
				    layout: {
				    	type: 'vbox',
				    	align: 'stretch'
				    },
				    border: 0,
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 280
				    },
				    defaultType: 'displayfield',
				    items: [{
				            xtype: 'fieldset',
							border: 0,
							padding: 10,
				            title: '',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '95%'
				            },
				       items: [{
				        xtype: 'combobox',
				        name: 'maritalStatusId',
				        fieldLabel: 'Marital Status',
				        emptyText: 'Select One',
				        store: me.maritalStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        name: 'ethnicityId',
				        fieldLabel: 'Ethnicity',
				        emptyText: 'Select One',
				        store: me.ethnicitiesStore,
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
				        store: me.gendersStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        itemId: 'citizenship',
				        name: 'citizenshipId',
				        fieldLabel: 'Citizenship',
				        emptyText: 'Select One',
				        store: me.citizenshipsStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        fieldLabel: 'Country of citizenship',
				        itemId: 'countryOfCitizenship',
				        name: 'countryOfCitizenship'
				    },{
				        xtype: 'combobox',
				        name: 'veteranStatusId',
				        fieldLabel: 'Veteran Status',
				        emptyText: 'Select One',
				        store: me.veteranStatusesStore,
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
				        itemId: 'childcareNeeded',
				        columns: 1,
				        items: [
				            {boxLabel: "Yes", name: "childCareNeeded", inputValue:"true"},
				            {boxLabel: "No", name: "childCareNeeded", inputValue:"false"}]
				    },{
				        xtype: 'combobox',
				        itemId: 'childcareArrangement',
				        name: 'childCareArrangementId',
				        fieldLabel: 'If yes, what are your childcare arrangements?',
				        emptyText: 'Select One',
				        store: me.childCareArrangementsStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: "radiogroup",
				        itemId: 'isEmployed',
				        fieldLabel: "Are you employed?",
				        columns: 1,
				        items: [
				            {boxLabel: "Yes", name: "employed", inputValue:"true"},
				            {boxLabel: "No", name: "employed", inputValue:"false"}]
				    },{
				        fieldLabel: 'Place of employment',
				        itemId: 'placeOfEmployment',
				        name: 'placeOfEmployment'
				    },{
				        xtype: 'combobox',
				        name: 'shift',
				        itemId: 'shift',
				        fieldLabel: 'Shift',
				        emptyText: 'Select One',
				        store: me.employmentShiftsStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        fieldLabel: 'Wage',
				        itemId: 'wage',
				        name: 'wage'
				    },{
				        fieldLabel: 'Total hours worked weekly while attending school',
				        itemId: 'totalHoursWorkedPerWeek',
				        name: 'totalHoursWorkedPerWeek'
				    }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});