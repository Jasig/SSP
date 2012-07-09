Ext.define('Ssp.store.Tools', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Tool',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ group:'beta', name: "Profile", toolType: "Profile", active: true },
           { group:'beta', name: "Student Intake", toolType: "StudentIntake", active: true },
           { group:'beta', name: "Action Plan", toolType: "ActionPlan", active: true },
           { group:'beta', name: "Journal", toolType: "Journal", active: true },
           { group:'rc1', name: "Early Alert", toolType: "EarlyAlert", active: true },
           { group:'rc1', name: "SIS", toolType: "StudentInformationSystem", active: true }
           ],          
           
           /*
           { group:'rc1', name: "Documents", toolType: "StudentDocuments", active: false },
           { group:'rc1', name: "Disability Services", toolType: "DisabilityServices", active: false },
           { group:'rc1', name: "Displaced Workers", toolType: "DisplacedWorker", active: false },
           { group:'rc1', name: "Student Success", toolType: "StudentSuccess", active: false },
           */ 
           
    groupField: 'group'
});