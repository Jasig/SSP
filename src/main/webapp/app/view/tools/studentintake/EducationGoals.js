Ext.define("Ssp.view.tools.studentintake.EducationGoals", {
	extend: "Ext.form.Panel",
	id : "StudentIntakeEducationGoal",
	autoScroll: true,    
    width: "100%",
    height: "100%",
    bodyPadding: 5,

    // Fields will be arranged vertically, stretched to full width
    layout: "anchor",
    defaults: {
        anchor: "100%"
    },

    // The fields
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
                {boxLabel: "Uncertain", name: "educationGoal", inputValue:"1"},
                {boxLabel: "Associates Degree", name: "educationGoal", inputValue:"2"},
                {boxLabel: "Certificate", name: "educationGoal", inputValue:"3"},
                {boxLabel: "Short Term Certificate", name: "educationGoal", inputValue:"4"},
                {boxLabel: "Bachelor's", name: "educationGoal", inputValue:"5"},
                {boxLabel: "Workforce", name: "educationGoal", inputValue:"6"},
                {boxLabel: "Tech School", name: "educationGoal", inputValue:"7"},
                {boxLabel: "Military", name: "educationGoal", inputValue:"8"},
                {boxLabel: "Other", name: "educationGoal", inputValue:"9"}
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
    }],
	

	initComponent: function() {	
		this.superclass.initComponent.call(this, arguments);
	}
	
});