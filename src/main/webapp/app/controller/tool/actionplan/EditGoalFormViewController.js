Ext.define('Ssp.controller.tool.actionplan.EditGoalFormViewController', {
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
    	url: ''
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
		this.url = this.apiProperties.getItemUrl('personGoal');
		this.url = this.url.replace('{id}',this.person.get('id'));
    	this.url = this.apiProperties.createUrl( this.url ); 
		
		return this.callParent(arguments);
	},
    
    onSaveClick: function(button){
    	var form, url;
    	form = this.getView().getForm();
    	if ( form.isValid() )
    	{
    		var values = form.getValues();
    		this.goal.set('name',values.name);
    		this.goal.set('description',values.description);
    		this.goal.set('confidentialityLevel',{id: values.confidentialityLevelId});
    		console.log( this.goal );
    		this.apiProperties.makeRequest({
    			url: this.url,
    			method: 'POST',
    			jsonData: this.goal.data,
    			successFunc: function(response ,view){
    				Ext.Msg.alert('The record was saved successfully');
    			}
    		}); 	
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },
    
    onCancelClick: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }    
});