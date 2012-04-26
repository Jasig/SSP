Ext.define('Ssp.view.tools.studentintake.EducationPlans', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakeeducationplans',
	id : 'StudentIntakeEducationPlans',
	autoScroll: true,    
    width: '100%',
    height: '100%',
    bodyPadding: 5,

	stores: ['StudentStatuses',
			 'YesNo'],

    // Fields will be arranged vertically, stretched to full width
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },

    // The fields
    defaultType: 'displayfield',
    items: [{
            xtype: 'fieldset',
            title: 'Education Plans',
            defaultType: 'textfield',
            defaults: {
                anchor: '100%'
            },
       items: [{
        xtype: 'combobox',
        name: 'studentStatusId',
        fieldLabel: 'Student Status',
        emptyText: 'Select One',
        store: Ext.getStore('reference.StudentStatuses'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
        xtype: 'checkboxgroup',
        fieldLabel: 'Check all that you have completed',
        columns: 1,
        items: [
            {boxLabel: 'New Student Orientation', name: 'newOrientationComplete'},
            {boxLabel: 'Registered for Classes', name: 'registeredForClasses'}
        ]
    },{
        xtype: "radiogroup",
        fieldLabel: "Have your parents obtained a college degree?",
        columns: 1,
        items: [
            {boxLabel: "Yes", name: "collegeDegreeForParents", inputValue:"true"},
            {boxLabel: "No", name: "collegeDegreeForParents", inputValue:"false"}]
    },{
        xtype: "radiogroup",
        fieldLabel: "Require special accommodations?",
        columns: 1,
        items: [
            {boxLabel: "Yes", name: "specialNeeds", inputValue:"true"},
            {boxLabel: "No", name: "specialNeeds", inputValue:"false"}]
    },{
            xtype: 'radiogroup',
            fieldLabel: 'What grade did you typically earn at your highest level of education?',
            items: [
                {boxLabel: 'A', name: 'gradeTypicallyEarned', inputValue: "A"},
                {boxLabel: 'A-B', name: 'gradeTypicallyEarned', inputValue: "A-B"},
                {boxLabel: 'B', name: 'gradeTypicallyEarned', inputValue: "B"},
                {boxLabel: 'B-C', name: 'gradeTypicallyEarned', inputValue: "B-C"},
                {boxLabel: 'C', name: 'gradeTypicallyEarned', inputValue: "C"},
                {boxLabel: 'C-D', name: 'gradeTypicallyEarned', inputValue: "C-D"},
                {boxLabel: 'D', name: 'gradeTypicallyEarned', inputValue: "D"},
                {boxLabel: 'D-F', name: 'gradeTypicallyEarned', inputValue: "D-F"},
                {boxLabel: 'F', name: 'gradeTypicallyEarned', inputValue: "F"}
        		]
        }]
    }]
	
});