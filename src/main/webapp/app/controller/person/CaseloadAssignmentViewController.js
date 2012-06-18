Ext.define('Ssp.controller.person.CaseloadAssignmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
     	formUtils: 'formRendererUtils',
        person: 'currentPerson'
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
		return this.callParent(arguments);
    },
    
    onSaveClick: function(button){
		Ext.Msg.alert('Attention', 'This feature is not yet available.');
    },
    
    onCancelClick: function(button){
		this.loadStudentToolsView();
    },
    
    loadStudentToolsView: function(){
    	// Display the Student Record
    	var comp = this.formUtils.loadDisplay('studentrecord', 'toolsmenu', true, {flex:1});    	
    	comp = this.formUtils.loadDisplay('studentrecord', 'tools', false, {flex:4});
    }
});