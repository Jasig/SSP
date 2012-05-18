Ext.define('Ssp.controller.tool.actionplan.AddGoalFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	goal: 'currentGoal',
    	person: 'currentPerson'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan',
    	personGoalUrl: ''
    },    
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}
	},
    
	constructor: function(){
		this.personGoalUrl = this.apiProperties.getItemUrl('personGoal');
		this.personGoalUrl = this.personGoalUrl.replace('{id}',this.person.get('id'));
    	
		return this.callParent(arguments);
	},
    
    onSaveClick: function(button){
    	console.log('AddGoalFormViewController->onAddClick');
    	var form, url;
    	form = this.getView().getForm();
    	if ( form.isValid() )
    	{
    		form.updateRecord();
    		url = this.apiProperties.createUrl( this.personGoalUrl );
    		console.log(this.goal);
    		/*
    		this.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: this.goal.data,
    			successFunc: function(response ,view){
    				Ext.Msg.alert('The record was saved successfully');
    			}
    		});
    		*/    	
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },
    
    onCancelClick: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }    
});