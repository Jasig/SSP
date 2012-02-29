Ext.define("Ssp.view.tools.studentintake.EducationGoals", {
	extend: "Ext.form.Panel",
	id : "StudentIntakeEducationGoals",
    
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
                {boxLabel: "Uncertain", name: "goal"},
                {boxLabel: "Associates Degree", name: "goal"},
                {boxLabel: "Certificate", name: "goal"},
                {boxLabel: "Short Term Certificate", name: "goal"},
                {boxLabel: "Bachelor's", name: "goal"},
                {boxLabel: "Workforce", name: "goal"},
                {boxLabel: "Tech School", name: "goal"},
                {boxLabel: "Military", name: "goal"},
                {boxLabel: "Other", name: "goal"}
        		]
        },
        {
            xtype: "radiogroup",
            fieldLabel: "How sure are you about your major?",
            columns: 1,
            items: [
                {boxLabel: "Very Unsure", name: "confidenceInMajor", value: 1},
                {boxLabel: "", name: "confidenceInMajor", value: 2},
                {boxLabel: "", name: "confidenceInMajor", value: 3},
                {boxLabel: "", name: "confidenceInMajor", value: 4},
                {boxLabel: "Very Sure", name: "confidenceInMajor", value: 5}
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