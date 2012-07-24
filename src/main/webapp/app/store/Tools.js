Ext.define('Ssp.store.Tools', {
    extend: 'Ext.data.Store',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    model: 'Ssp.model.Tool',
    autoLoad: false,
    
    constructor: function(){
    	var me=this;
    	me.callParent( arguments );
    	
    	var sspTools = [{ group:'beta', name: "Profile", toolType: "profile", active: true },
    	                { group:'beta', name: "Student Intake", toolType: "studentintake", active: true },
    	                { group:'beta', name: "Action Plan", toolType: "actionplan", active: true },
    	                { group:'beta', name: "Journal", toolType: "journal", active: true },
    	                { group:'rc1', name: "Early Alert", toolType: "earlyalert", active: true }]         
    	                
    	                /*
    	                { group:'rc1', name: "SIS", toolType: "StudentInformationSystem", active: true },
    	                { group:'rc1', name: "Documents", toolType: "StudentDocuments", active: false },
    	                { group:'rc1', name: "Disability Services", toolType: "DisabilityServices", active: false },
    	                { group:'rc1', name: "Displaced Workers", toolType: "DisplacedWorker", active: false },
    	                { group:'rc1', name: "Student Success", toolType: "StudentSuccess", active: false },
    	                */
     	
     	// set the model
     	me.loadData( me.applySecurity( sspTools ) )
  
    	return me;
    },
    
    applySecurity: function( tools ){
    	var me=this;
    	var sspSecureTools = [];
    	
    	Ext.Array.each( tools, function( tool, index){
    		var toolSecurityIdentifier = tool.toolType.toUpperCase() + '_TOOL';
    		if ( me.authenticatedPerson.hasAccess( toolSecurityIdentifier ) )
    		{
    			sspSecureTools.push( tool );
    		}
    	});
    	
    	return sspSecureTools;
    }
           
    //groupField: 'group'
});