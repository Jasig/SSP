Ext.define("Ssp.view.tools.studentintake.EducationGoals", {
	extend: "Ext.form.Panel",
	alias: 'widget.studentintakeeducationgoals',
	id : "StudentIntakeEducationGoals",   
    width: "100%",
    height: "100%", 
    initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
				    bodyPadding: 5,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 200
				    },
				    defaultType: "radiogroup",
				    items: [{
				            xtype: "fieldset",
				            border: 0,
				            title: "",
				            id: 'StudentIntakeEducationGoalsFieldSet',
				            defaultType: "textfield",
				            defaults: {
				                anchor: "95%"
				            },
				       items: [{
				            xtype: "radiogroup",
				            id: 'StudentIntakeEducationGoalsRadioGroup',
				            fieldLabel: "Education/Career Goal",
				            allowBlank: true,
				            columns: 1
				        }]
				    },{
			            xtype: "fieldset",
			            border: 0,
			            title: '',
			            defaultType: "textfield",
			            defaults: {
			                anchor: "95%"
			            },
			       items: [{
				        fieldLabel: 'What is your planned major?',
				        name: 'plannedMajor'
				    },{
			            xtype: "radiogroup",
			            fieldLabel: "How sure are you about your major?",
			            columns: 1,
			            items: [
			                {boxLabel: "Very Unsure", name: "howSureAboutMajor", inputValue: "1"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "2"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "3"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "4"},
			                {boxLabel: "Very Sure", name: "howSureAboutMajor", inputValue: "5"}
			        		]
			        },{
				        xtype: "radiogroup",
				        fieldLabel: 'Have you decided on a career/occupation?',
				        columns: 1,
				        itemId: 'careerDecided',
				        items: [
				            {boxLabel: "Yes", name: "careerDecided", inputValue:"true"},
				            {boxLabel: "No", name: "careerDecided", inputValue:"false"}]
					},{
				        fieldLabel: 'What is your planned occupation?',
				        name: 'plannedOccupation'
				    },{
			            xtype: "radiogroup",
			            fieldLabel: "How sure are you about your occupation?",
			            columns: 1,
			            items: [
			                {boxLabel: "Very Unsure", name: "howSureAboutOccupation", inputValue: "1"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "2"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "3"},
			                {boxLabel: "", name: "howSureAboutOccupation", inputValue: "4"},
			                {boxLabel: "Very Sure", name: "howSureAboutOccupation", inputValue: "5"}
			        		]
			        },{
				        xtype: 'radiogroup',
				        fieldLabel: 'Are you confident your abilities are compatible with the career field?',
				        columns: 1,
				        itemId: 'confidentInAbilities',
				        items: [
				            {boxLabel: "Yes", name: "confidentInAbilities", inputValue:"true"},
				            {boxLabel: "No", name: "confidentInAbilities", inputValue:"false"}]
					},{
				        xtype: "radiogroup",
				        fieldLabel: 'Do you need additional information about which academic programs may lead to a future career?',
				        columns: 1,
				        itemId: 'additionalAcademicProgramInformationNeeded',
				        items: [
				            {boxLabel: "Yes", name: "additionalAcademicProgramInformationNeeded", inputValue:"true"},
				            {boxLabel: "No", name: "additionalAcademicProgramInformationNeeded", inputValue:"false"}]
					}]
				    
				    }]
				});
		
		return this.callParent(arguments);
	}	
});