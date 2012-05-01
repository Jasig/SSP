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
				    layout: "anchor",
				    defaults: {
				        anchor: "100%"
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 200
				    },
				    defaultType: "displayfield",
				    items: [{
				            xtype: "fieldset",
				            title: "Education Goals",
				            defaultType: "textfield",
				            defaults: {
				                anchor: "100%"
				            },
				       items: [{
				            xtype: "radiogroup",
				            fieldLabel: "Education/Career Goal",
				            columns: 1,
				            items: [
				                {boxLabel: "Uncertain", name: "educationGoalId", inputValue:"9bf33704-e41e-4922-bc7f-07b98b276824"},
				                {boxLabel: "Associates Degree", name: "educationGoalId", inputValue:"d25e224b-a0ca-48f0-ac30-1ddf5bdb9e0d"},
				                {boxLabel: "Certificate", name: "educationGoalId", inputValue:"00afdf46-2f0e-46e4-9d56-bc45b9266642"},
				                {boxLabel: "Short Term Certificate", name: "educationGoalId", inputValue:"5cccdca1-9a73-47e8-814f-134663a2ae67"},
				                {boxLabel: "Bachelor's", name: "educationGoalId", inputValue:"efeb5536-d634-4b79-80bc-1e1041dcd3ff"},
				                {boxLabel: "Workforce", name: "educationGoalId", inputValue:"d632046f-1fbf-4361-ac1e-3ca67f78e104"},
				                {boxLabel: "Tech School", name: "educationGoalId", inputValue:"7e3e6f05-612c-4636-a370-7b038e98510f"},
				                {boxLabel: "Military", name: "educationGoalId", inputValue:"6c466885-d3f8-44d1-a301-62d6fe2d3553"},
				                {boxLabel: "Other", name: "educationGoalId", inputValue:"78b54da7-fb19-4092-bb44-f60485678d6b"}
				        		]
				        },
				        {
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
				        fieldLabel: 'What is your planned occupation?',
				        name: 'plannedOccupation'
				    }]
				    }]
				});
		
	     this.callParent(arguments);
	}	
});