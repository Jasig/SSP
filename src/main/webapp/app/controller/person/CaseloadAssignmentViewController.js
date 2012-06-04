Ext.define('Ssp.controller.person.CaseloadAssignmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
        person: 'currentPerson',
        specialServiceGroupsStore: 'specialServiceGroupsStore',
        referralSourcesStore: 'referralSourcesStore',
        serviceReasonsStore: 'serviceReasonsStore',
        studentTypesStore: 'studentTypesStore'
    },
    control: {
    	'saveButton':{
    		click: 'onSaveClick'
    	},
    	
    	'cancelButton': {
    		click: 'onCancelClick'
    	}
    },
    
	init: function() {
		var personal = Ext.ComponentQuery.query('.editperson')[0];
		personal.loadRecord( this.person );
		
		this.specialServiceGroupsStore.load();
		this.referralSourcesStore.load();
		this.serviceReasonsStore.load();
		this.studentTypesStore.load();
		
		return this.callParent(arguments);
    },
    
    onSaveClick: function(button){
		Ext.Msg.alert('Attention', 'This feature is not yet available.');
    },
    
    onCancelClick: function(button){
		this.loadStudentToolsView();
    },
    
    loadStudentToolsView: function(){
    	var comp = this.formUtils.loadDisplay('studentrecord', 'toolsmenu', true, {flex:1});    	
    	comp = this.formUtils.loadDisplay('studentrecord', 'tools', false, {flex:4});
    }
});