Ext.define('Ssp.controller.tool.actionplan.EditGoalFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentGoal',
    	person: 'currentPerson'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan',
    	url: ''
    },    
    control: {
    	combo: '#confidentialityLevel',
    	
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}
	},
  
	init: function() {
		this.getView().getForm().loadRecord( this.model );
		this.getCombo().setValue( this.model.get('confidentialityLevel').id );
		return this.callParent(arguments);
    },	
	
	constructor: function(){
		this.url = this.apiProperties.getItemUrl('personGoal');
		this.url = this.url.replace('{id}',this.person.get('id'));
    	this.url = this.apiProperties.createUrl( this.url );
	
		return this.callParent(arguments);
	},
    
    onSaveClick: function(button){
    	var me=this;
    	var model = this.model;
    	var form, url, goalId, successFunc;
    	form = this.getView().getForm();
    	id = model.get('id');
    	if ( form.isValid() )
    	{
    		var values = form.getValues();
    		model.set('name',values.name);
    		model.set('description',values.description);
    		model.set('confidentialityLevel',{id: values.confidentialityLevelId});
    		
    		successFunc = function(response ,view){
				me.loadDisplay();
			};
			
    		if (id == "")
    		{
    			// add
    			this.apiProperties.makeRequest({
	    			url: this.url,
	    			method: 'POST',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});
    		}else{
    			// edit
	    		this.apiProperties.makeRequest({
	    			url: this.url+id,
	    			method: 'PUT',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});    			
    		}
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },
    
    onCancelClick: function(button){
    	this.loadDisplay();
    },
    
    loadDisplay: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
});