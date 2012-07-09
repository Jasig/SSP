Ext.define('Ssp.controller.tool.actionplan.DisplayStrengthsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson',
    	model: 'currentPerson'
    },
    
    config: {
    	url: ''
    },
    
    control: {  	
    	saveButton: {
    		selector: '#saveButton',
    		listeners: {
    			click: 'onSaveClick'
    		}
    	},
    	
    	strengthsField: {
    		selector: '#strengths',
    		listeners: {
    			change: 'onStrengthsChange'
    		}
    	}
	},
	
	init: function() {
		var me=this;
		me.url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person'));
		me.getView().getForm().loadRecord( me.model );
		me.getSaveButton().disabled=true;
    	
		me.getStrengthsField().setDisabled( !me.authenticatedPerson.hasPermission('ROLE_PERSON_WRITE') );

		return me.callParent(arguments);
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