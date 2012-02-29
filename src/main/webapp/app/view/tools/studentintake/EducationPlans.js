Ext.define('Ssp.view.tools.studentintake.EducationPlans', {
	extend: 'Ext.form.Panel',
	id : 'StudentIntakeEducationPlans',
    
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
        name: 'studentStatus',
        fieldLabel: 'Student Status',
        emptyText: 'Select One',
        store: Ext.getStore('reference.StudentStatuses'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: false,
        forceSelection: true
	},{
        xtype: 'checkboxgroup',
        fieldLabel: 'Check all that you have completed',
        columns: 1,
        items: [
            {boxLabel: 'New Student Orientation', name: 'newStudentOrientation'},
            {boxLabel: 'Registered for Classes', name: 'registeredForClasses'}
        ]
    },{
        xtype: 'combobox',
        name: 'parentsObtainedADegree',
        fieldLabel: 'Have your parents obtained a college degree?',
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
        name: 'requireSpecialAccommodation',
        fieldLabel: 'Require Special Accommodation',
        emptyText: 'Select One',
        store: Ext.getStore('reference.YesNo'),
        valueField: 'id',
        displayField: 'name',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: true
	},{
            xtype: 'radiogroup',
            fieldLabel: 'What grade did you typically earn at your highest level of education?',
            columns: 1,
            items: [
                {boxLabel: 'A', name: 'averageGradeInSchool'},
                {boxLabel: 'A-B', name: 'averageGradeInSchool'},
                {boxLabel: 'B', name: 'averageGradeInSchool'},
                {boxLabel: 'B-C', name: 'averageGradeInSchool'},
                {boxLabel: 'C', name: 'averageGradeInSchool'},
                {boxLabel: 'C-D', name: 'averageGradeInSchool'},
                {boxLabel: 'D', name: 'averageGradeInSchool'},
                {boxLabel: 'D-F', name: 'averageGradeInSchool'},
                {boxLabel: 'F', name: 'averageGradeInSchool'}
        		]
        }]
    }],
	

	initComponent: function() {	
		this.superclass.initComponent.call(this, arguments);
	}
	
});