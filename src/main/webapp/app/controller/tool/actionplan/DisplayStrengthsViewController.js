Ext.define('Ssp.controller.tool.actionplan.DisplayStrengthsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	model: 'currentPerson'
    },
    
    config: {
    	url: ''
    },
    
    control: {  	
    	saveButton: '#saveButton',
    	
    	'saveButton': {
    		click: 'onSaveClick'
    	},
    	
    	'strengths': {
    		change: 'onStrengthsChange'
    	}
	},
	
	init: function() {
		this.url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('person'));
		this.getView().getForm().loadRecord(this.model);
		this.getSaveButton().disabled=true;
		return this.callParent(arguments);
    },
    
    onSaveClick: function(button) {
		var me=this;
		var form = this.getView().getForm();
		if (form.isValid())
		{
			form.updateRecord();
			this.apiProperties.makeRequest({
				url: me.url+me.model.get('id'),
				method: 'PUT',
				jsonData: me.model.data,
				successFunc: successFunc = function(response ,view){
					me.model.commit();
					me.setSaveButtonState();
				}
			});		
		}else{
			Ext.Msg.alert('Unable to save strengths. Please correct the errors in the form.');
		}	
    },
    
    onStrengthsChange: function(comp, oldValue, newValue, eOpts){
    	this.setSaveButtonState();
    },
    
    setSaveButtonState: function(){
    	this.getSaveButton().disabled = !this.getView().getForm().isDirty();
    }
});