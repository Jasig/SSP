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
				            id: 'StudentIntakeEducationGoalsRadioGroup',
				            fieldLabel: "Education/Career Goal",
				            columns: 1
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
		
		return this.callParent(arguments);
	}	
});