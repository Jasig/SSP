Ext.define('Ssp.store.Tools', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Tool',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ group:'alpha', name: "Profile", toolType: "Profile", active: true },
           { group:'alpha', name: "Student Intake", toolType: "StudentIntake", active: true },
           { group:'alpha', name: "Action Plan", toolType: "ActionPlan", active: true },
           { group:'alpha', name: "Journal", toolType: "Journal", active: true },
           { group:'alpha', name: "Early Alert", toolType: "EarlyAlert", active: true },
           { group:'beta', name: "Counseling Services", toolType: "CounselingServices", active: false },
           { group:'beta', name: "Documents", toolType: "StudentDocuments", active: false },
           { group:'beta', name: "Disability Services", toolType: "DisabilityServices", active: false },
           { group:'beta', name: "Displaced Workers", toolType: "DisplacedWorker", active: false },
           { group:'beta', name: "Student Success", toolType: "StudentSuccess", active: false },
           ],
           
    groupField: 'group'
});